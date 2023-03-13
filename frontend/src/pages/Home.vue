<template>
  <div id="map" ref="mapel"></div>
</template>

<script setup>
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue';
import mapboxgl from 'mapbox-gl';
import 'mapbox-gl/dist/mapbox-gl.css';
import MapboxLanguage from '@mapbox/mapbox-gl-language';
import { showToast } from 'vant';
import { useRouter } from 'vue-router';
import { getLocalGeo } from '../utils/index';
import { useStore } from '../stores/user';
import { request } from '../http';

const store = useStore();
const router = useRouter();
const mapel = ref(null);
let map; // 地图
let driverTimer = null;
let passengerTimer = null;
const start = {
  center: [80, 80],
  zoom: 1,
};

function drawPoint(sourceId, layerId, layout, point) {
  map.addSource(sourceId, {
    type: 'geojson',
    data: {
      type: 'FeatureCollection',
      features: [
        {
          type: 'Feature',
          geometry: {
            type: 'Point',
            coordinates: point,
          },
        },
      ],
    },
  });
  map.addLayer({
    id: layerId,
    type: 'symbol',
    source: sourceId,
    layout: {
      ...layout,
    },
  });
}

function removePoint(id) {
  if (map.getLayer(`${id}-layer`)) map.removeLayer(`${id}-layer`);
  if (map.getSource(`${id}-source`)) map.removeSource(`${id}-source`);
}

function drawBusPoint(id, point, paint) {
  drawPoint(
    `${id}-source`,
    `${id}-layer`,
    {
      'icon-image': 'bus-point',
      'icon-size': 0.2,
      ...paint,
    },
    point
  );
}

function drawBusStation(id, point) {
  drawPoint(
    `${id}-source`,
    `${id}-layer`,
    {
      'icon-image': 'bus-station',
      'icon-size': 0.2,
    },
    point
  );
}

function drawStartPoint(id, point) {
  drawPoint(
    `${id}-source`,
    `${id}-layer`,
    {
      'icon-image': 'start-point',
      'icon-size': 0.2,
    },
    point
  );
}

function drawEndPoint(id, point) {
  drawPoint(
    `${id}-source`,
    `${id}-layer`,
    {
      'icon-image': 'end-point',
      'icon-size': 0.2,
    },
    point
  );
}

function drawBusPath(id, path) {
  map.addSource(`${id}-source`, {
    type: 'geojson',
    data: {
      type: 'Feature',
      properties: {},
      geometry: {
        type: 'LineString',
        coordinates: path,
      },
    },
  });
  map.addLayer({
    id: `${id}-layer`,
    type: 'line',
    source: `${id}-source`,
    layout: {
      'line-join': 'round',
      'line-cap': 'round',
    },
    paint: {
      'line-color': '#006efc',
      'line-width': 4,
    },
  });
}

function loadImage(url, id) {
  return new Promise((resolve, reject) => {
    map.loadImage(url, (error, image) => {
      if (error) reject(error);
      map.addImage(id, image);
      return resolve(image);
    });
  });
}

function saveGps() {
  getLocalGeo().then((pos) => {
    const longitude = pos.coords.longitude;
    const latitude = pos.coords.latitude;
    map.easeTo({
      center: [longitude, latitude],
      zoom: 13,
    });
    removePoint('driverBusPoint');
    drawBusPoint('driverBusPoint', [longitude, latitude]);
    request(
      '/common/savePosition',
      {
        latitude,
        longitude,
        tourId: store.$state.tourId,
      },
      'POST'
    ).then((res) => {
      if (res.code !== 500) {
        if (res.data.isEnd === '2') {
          router.push('/login');
        }
      } else {
        showToast(res.message);
      }
    });
  });
}

const data = reactive({
  busPointList: [],
});

function queryGps() {
  const param = {};
  if (store.$state.busNo) param.busNo = store.$state.busNo;
  if (store.$state.headingType) param.headingType = store.$state.headingType;
  request('/common/queryPositions', param, 'GET').then((res) => {
    if (res.code !== 500) {
      if (data.busPointList.length) {
        data.busPointList.forEach((id) => removePoint(id));
        data.busPointList = [];
      }
      res.data.forEach((item) => {
        data.busPointList.push(item.id);
        drawBusPoint(item.id, [item.longitude, item.latitude], {
          'text-field': item.busNo,
          'text-offset': [0, 1],
          'text-size': 12,
        });
      });
    } else {
      showToast(res.message);
    }
  });
}

function mapPointInit() {
  if (store.$state.type === 'driver') {
    saveGps();
    driverTimer = setInterval(saveGps, 1000 * 5);
  } else {
    queryGps();
    passengerTimer = setInterval(queryGps, 1000 * 5);
  }
}

function init(latitude, longitude) {
  mapboxgl.accessToken = 'pk.eyJ1IjoiZW5jYWlrIiwiYSI6ImNrajJsY2NiZjI3aTAycnAzMmxjNHhkc2kifQ.AB2MHM2K_uAkMIHZYsm_dg';
  map = new mapboxgl.Map({
    container: mapel.value,
    style: 'mapbox://styles/mapbox/streets-v12',
    ...start,
  });
  if (mapboxgl.getRTLTextPluginStatus() !== 'loaded') {
    mapboxgl.setRTLTextPlugin('https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-rtl-text/v0.2.3/mapbox-gl-rtl-text.js');
  }
  map.addControl(new MapboxLanguage({ defaultLanguage: 'zh-Hans' }));
  map.addControl(new mapboxgl.NavigationControl(), 'bottom-right');
  map.on('style.load', () => {
    map.setFog({});
    map.flyTo({
      center: [longitude, latitude],
      zoom: 13,
      duration: 5000,
      essential: true,
    });
    Promise.all([
      loadImage('/bus-point.png', 'bus-point'),
      loadImage('/start-point.png', 'start-point'),
      loadImage('/end-point.png', 'end-point'),
      loadImage('/bus-station.png', 'bus-station'),
    ]).then(() => mapPointInit());
  });
}

onMounted(() => {
  console.log(store.$state);
  getLocalGeo().then((res) => {
    init(res.coords.latitude, res.coords.longitude);
  });
});

onBeforeUnmount(() => {
  map = null;
  if (driverTimer) {
    clearInterval(driverTimer);
    driverTimer = null;
  }
  if (passengerTimer) {
    clearInterval(passengerTimer);
    passengerTimer = null;
  }
});
</script>

<style lang="less" scoped>
#map {
  position: absolute;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
}
</style>

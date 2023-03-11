<template>
  <div id="map" ref="mapel"></div>
</template>

<script setup>
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue';
import mapboxgl from 'mapbox-gl';
import 'mapbox-gl/dist/mapbox-gl.css';
import MapboxLanguage from '@mapbox/mapbox-gl-language';
import { getLocalGeo } from '../utils/index';

const mapel = ref(null);
let map; // 地图
const start = {
  center: [80, 80],
  zoom: 1,
};
const localGeo = reactive({
  latitude: 0,
  longitude: 0,
  id: '',
  busId: '',
});

const busData = reactive({
  busPoint: [
    {
      id: '1',
      coords: [118.63915644371428, 32.06923575757618],
    },
    {
      id: '2',
      coords: [118.62871802769098, 32.061531211133115],
    },
    {
      id: '3',
      coords: [118.6438032224608, 32.05405432712121],
    },
    {
      id: '4',
      coords: [87.57372966328714, 43.82961794634063],
    },
  ],
  busPath: [
    {
      id: '1001',
      coords: [
        [118.64712823763267, 32.072525079971356],
        [118.64418729660593, 32.06844420015055],
        [118.63837893807931, 32.071060968466384],
        [118.637423132246, 32.07068714900531],
        [118.63569532939295, 32.06682425843093],
        [118.63462923827149, 32.0644565996854],
        [118.633085244232, 32.060593445940754],
        [118.63212943839875, 32.05841256129503],
        [118.63095306198773, 32.05582658784478],
        [118.62999725615447, 32.054081793379794],
        [118.62613727105702, 32.049532708505595],
      ],
    },
    {
      id: '1002',
      coords: [
        [118.62676222102607, 32.06398929137454],
        [118.63003401791713, 32.0623692707468],
        [118.62812240625055, 32.061901951771645],
        [118.62867383269264, 32.0602507389395],
        [118.62988697086632, 32.05947185461673],
        [118.63212943839875, 32.05838140542352],
        [118.63536447352715, 32.05660550320273],
        [118.63790103516266, 32.055390392347945],
        [118.6401802644578, 32.05741556814101],
        [118.6428271113814, 32.06028189417435],
        [118.64598862298584, 32.05844371715591],
        [118.64345206135033, 32.0557331175565],
        [118.64529014949153, 32.054704938079524],
        [118.6433785378249, 32.052492755186094],
        [118.64470196128673, 32.05162033032613],
      ],
    },
  ],
});

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

function drawBusPoint(id, point) {
  drawPoint(
    `${id}-source`,
    `${id}-layer`,
    {
      'icon-image': 'bus-point',
      'icon-size': 0.2,
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

function init() {
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
      center: [localGeo.longitude, localGeo.latitude],
      zoom: 13,
      duration: 5000,
      essential: true,
    });
    Promise.all([
      loadImage('/bus-point.png', 'bus-point'),
      loadImage('/start-point.png', 'start-point'),
      loadImage('/end-point.png', 'end-point'),
      loadImage('/bus-station.png', 'bus-station'),
    ]).then(() => {
      busData.busPath.forEach(({ id, coords }) => {
        drawBusPath(id, coords);
        coords.forEach((point, index) => {
          if (index === 0) {
            drawStartPoint(`${id}-start-${index}`, point);
          } else if (index === coords.length - 1) {
            drawEndPoint(`${id}-end-${index}`, point);
          } else {
            drawBusStation(`${id}-station-${index}`, point);
          }
        });
      });
      busData.busPoint.forEach(({ id, coords }) => {
        drawBusPoint(id, coords);
      });
    });
  });
}

onMounted(() => {
  getLocalGeo().then((res) => {
    localGeo.latitude = res.coords.latitude;
    localGeo.longitude = res.coords.longitude;
    init();
  });
});

onBeforeUnmount(() => {
  map = null;
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

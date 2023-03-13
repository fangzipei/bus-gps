<template>
  <van-search v-model="value" placeholder="请输入搜索关键词" />
  <van-list v-model:loading="loading" :finished="finished" finished-text="没有更多了">
    <template v-for="bus in dataList.busList" :key="bus.id">
      <van-cell :title="bus.busNo + '(' + bus.upStart + '-' + bus.upEnd + ')'" :label="bus.runTime" is-link @click="onBusClick(bus, '1')" />
      <van-cell :title="bus.busNo + '(' + bus.downStart + '-' + bus.downEnd + ')'" :label="bus.runTime" is-link @click="onBusClick(bus, '2')" />
    </template>
  </van-list>
</template>
<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from '../stores/user';
import { request } from '../http';

const router = useRouter();
const store = useStore();

const value = ref('');
const loading = ref(false);
const finished = ref(false);
const dataList = reactive({
  busList: [],
});

function onBusClick(bus, headingType) {
  store.$patch({ busNo: bus.busNo, headingType });
  router.push('/home');
}

onMounted(() => {
  request('/common/getBusInfos', null, 'GET').then((res) => {
    dataList.busList = res.data;
  });
});
</script>
<style lang="less" scoped></style>

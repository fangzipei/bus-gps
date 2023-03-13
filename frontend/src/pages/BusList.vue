<template>
  <van-search v-model="value" placeholder="请输入搜索关键词" />
  <van-list v-model:loading="loading" :finished="finished" finished-text="没有更多了">
    <template v-for="bus in dataList.busList" :key="bus.id">
      <van-cell :title="bus.busNo + '(' + bus.upStart + '-' + bus.upEnd + ')'" :label="bus.runTime" is-link />
      <van-cell :title="bus.busNo + '(' + bus.downStart + '-' + bus.downEnd + ')'" :label="bus.runTime" is-link />
    </template>
  </van-list>
</template>
<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useStore } from '../stores/user';
import { request } from '../http';

const store = useStore();

const value = ref('');
const list = ref([]);
const loading = ref(false);
const finished = ref(false);
const dataList = reactive({
  busList: [],
});

onMounted(() => {
  request('/common/getBusInfos', null, 'GET').then((res) => {
    dataList.busList = res.data;
  });
});
</script>
<style lang="less" scoped></style>

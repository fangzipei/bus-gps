<template>
  <van-search v-model="value" placeholder="请输入搜索关键词" />
  <van-list v-model:loading="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
    <van-cell v-for="item in list" :key="item.id" :title="item.number" :value="item.duringTime" is-link />
  </van-list>
</template>
<script setup>
import { ref } from 'vue';
import { useStore } from '../stores/user';

const store = useStore();

const value = ref('');
const list = ref([]);
const loading = ref(false);
const finished = ref(false);
const onLoad = () => {
  setTimeout(() => {
    for (let i = 0; i < 10; i++) {
      list.value.push({
        id: i + 1,
        number: `${i + 1}路(xxx站-xxx站)`,
        duringTime: '6:00-18:00',
        status: Math.round(Math.random() * 2),
      });
    }
    loading.value = false;
    if (list.value.length >= 40) {
      finished.value = true;
    }
  }, 1000);
};
</script>
<style lang="less" scoped></style>

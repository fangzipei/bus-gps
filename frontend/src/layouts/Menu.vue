<template>
  <van-sticky>
    <van-nav-bar title="公交司乘智联系统" left-text="返回" left-arrow @click-left="onNavLeftClick" />
  </van-sticky>
  <RouterView></RouterView>
  <van-tabbar v-if="store.$state.type !== 'driver'" v-model="active">
    <van-tabbar-item replace to="/home" icon="home-o">实时地图</van-tabbar-item>
    <van-tabbar-item replace to="/bus-list" icon="search">公交查询</van-tabbar-item>
  </van-tabbar>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from '../stores/user';

const router = useRouter();
const store = useStore();
const active = ref(0);

function onNavLeftClick() {
  router.push('/login');
}
</script>

<style lang="less" scoped></style>

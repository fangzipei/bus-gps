<template>
  <div class="login">
    <h1>公交司乘智联系统</h1>
    <van-row justify="center">
      <van-col span="6">
        <van-button type="primary" @click="onLoginClick('driver')">司机入口</van-button>
      </van-col>
      <van-col span="6">
        <van-button type="primary" @click="onLoginClick('passenger')"
          >乘客入口</van-button
        >
      </van-col>
    </van-row>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

function onLoginClick(type) {
  router.push({ name: 'home', params: { type } });
}
</script>

<style lang="less" scoped>
.login {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-image: url("../assets/bus.png"), linear-gradient(0deg, #fff, rgb(214,240,255));
  background-repeat: no-repeat;
  background-position: 50% 100%;
  background-size: 100%;
  .van-row {
    width: 100%;
    margin-top: 80px;
    margin-bottom: 200px;
  }
  .van-col+.van-col{
    margin-left: 24px;
  }
}
</style>

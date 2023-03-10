<template>
  <div class="login">
    <h1>公交司乘智联系统</h1>
    <van-row justify="center">
      <van-col span="6">
        <van-button type="primary" @click="onLoginClick('driver')">司机入口</van-button>
      </van-col>
      <van-col span="6">
        <van-button type="primary" @click="onLoginClick('passenger')">乘客入口</van-button>
      </van-col>
    </van-row>
  </div>
  <van-popup :show="formShow" position="bottom" @close="closePopup" :close-on-click-overlay="true">
    <DriverForm @formData="onformDataEmit"></DriverForm>
  </van-popup>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from '../stores/user';
import DriverForm from '../components/DriverForm.vue';
import { request } from '../http';

const store = useStore();
const router = useRouter();

const formShow = ref(false);

request('/busList', null, 'GET');

const onformDataEmit = (values) => {
  formShow.value = false;
  console.log(values);
  store.$patch({ type: 'driver' });
  router.push('/home');
};

const closePopup = () => {
  formShow.value = false;
};

function onLoginClick(type) {
  if (type === 'driver') {
    formShow.value = true;
  } else {
    store.$patch({ type });
    router.push('/home');
  }
}
</script>

<style lang="less" scoped>
.login {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-image: url('../assets/bus.png'), linear-gradient(0deg, #fff, rgb(214, 240, 255));
  background-repeat: no-repeat;
  background-position: 50% 100%;
  background-size: 100%;
  .van-row {
    width: 100%;
    margin-top: 80px;
    margin-bottom: 200px;
  }
  .van-col + .van-col {
    margin-left: 24px;
  }
}
</style>

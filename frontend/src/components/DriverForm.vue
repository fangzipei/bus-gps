<template>
  <van-form class="form" @submit="onSubmit">
    <van-cell-group inset>
      <van-field v-model="driverId" name="driverId" label="工号" placeholder="请输入工号" :rules="[{ required: true, message: '请填写工号' }]" />
    </van-cell-group>
    <div style="margin: 16px">
      <van-button round block type="primary" native-type="submit"> 提交 </van-button>
    </div>
  </van-form>
</template>

<script setup>
import { ref } from 'vue';

const emit = defineEmits(['formData']);
const driverId = ref('');

const onSubmit = (values) => {
  emit('formData', values);
};
</script>

<style lang="less" scoped>
.form {
  height: 100%;
  background: #e6e6e6;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>

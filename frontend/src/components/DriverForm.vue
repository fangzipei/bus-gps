<template>
  <van-form class="form" @submit="onSubmit">
    <van-cell-group inset>
      <van-field
        v-model="driverId"
        name="driverId"
        label="司机工号"
        placeholder="请输入司机工号"
        :rules="[{ required: true, message: '请填写司机工号' }]"
      />
      <van-field
        v-model="busId"
        name="busId"
        label="公交线路"
        placeholder="请输入公交线路"
        :rules="[{ required: true, message: '请填写公交线路' }]"
      />
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
const busId = ref('');

const onSubmit = (values) => {
  emit('formData', values);
};
</script>

<style lang="less" scoped>
.form {
  padding-top: 24px;
  background: #e6e6e6;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>

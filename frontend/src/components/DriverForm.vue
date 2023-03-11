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
        is-link
        readonly
        label="公交线路"
        placeholder="请选择公交线路"
        :rules="[{ required: true, message: '请填写公交线路' }]"
        @click="showPicker = true"
      />
      <van-popup v-model:show="showPicker" round position="bottom">
        <van-picker
          :columns="dataList.busList"
          :columns-field-names="customFieldName"
          :default-index="0"
          @cancel="showPicker = false"
          @confirm="onConfirm"
        />
      </van-popup>
    </van-cell-group>
    <div style="margin: 16px">
      <van-button round block type="primary" native-type="submit"> 提交 </van-button>
    </div>
  </van-form>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { request } from '../http';

const emit = defineEmits(['formData']);
const driverId = ref('');
const busId = ref('');
const showPicker = ref(false);
const customFieldName = { text: 'busNo', values: 'id' };
const dataList = reactive({
  busList: [],
});

request('/common/getBusInfos', null, 'GET').then((res) => {
  dataList.busList.value = res.data;
  console.log(dataList.busList);
});

const onConfirm = (value) => {
  busId.value = value;
  showPicker.value = false;
};

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

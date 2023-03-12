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
        v-model="busNo"
        name="busNo"
        is-link
        readonly
        label="公交线路"
        placeholder="请选择公交线路"
        :rules="[{ required: true, message: '请填写公交线路' }]"
        @click="showPicker = true"
      />
      <van-popup v-model:show="showPicker" round position="bottom">
        <van-picker :columns="dataList.busList" :columns-field-names="columnsField" @cancel="showPicker = false" @confirm="onConfirm" />
      </van-popup>
    </van-cell-group>
    <div style="margin: 16px">
      <van-button round block type="primary" native-type="submit"> 提交 </van-button>
    </div>
  </van-form>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { request } from '../http';

const emit = defineEmits(['formData']);
const driverId = ref('');
const busNo = ref('');
const showPicker = ref(false);
const columnsField = { text: 'busNo', value: 'busNo' };
const dataList = reactive({
  busList: [],
});

onMounted(() => {
  request('/common/getBusInfos', null, 'GET').then((res) => {
    dataList.busList = res.data;
  });
});

const onConfirm = ({ selectedOptions }) => {
  busNo.value = selectedOptions[0].busNo;
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

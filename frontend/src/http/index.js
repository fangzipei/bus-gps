import axios from 'axios';
import { showToast } from 'vant';
import { showMessage } from './status';

// 设置接口超时时间
axios.defaults.timeout = 60000;

// 请求地址，这里是动态赋值的的环境变量，下一篇会细讲，这里跳过
// @ts-ignore
axios.defaults.baseURL = import.meta.env.VITE_BASE_URL;

// http request 拦截器
axios.interceptors.request.use(
  (config) => {
    // 配置请求头
    config.headers = {
      // 'Content-Type':'application/x-www-form-urlencoded',   // 传参方式表单
      'Content-Type': 'application/json;charset=UTF-8', // 传参方式json
      token: '80c483d59ca86ad0393cf8a98416e2a1', // 这里自定义配置，这里传的是token
    };
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// http response 拦截器
axios.interceptors.response.use(
  (response) => {
    const { data } = response;
    if (data.code === 500) {
      showToast(data.message);
      return;
    }
    return data;
  },
  (error) => {
    const { response } = error;
    if (response) {
      showToast(showMessage(response.status));
      return Promise.reject(response.data);
    }
    showToast('网络连接异常,请稍后再试!');
  }
);

// 封装 GET POST 请求并导出
export function request(url = '', params = {}, type = 'POST') {
  // 设置 url params type 的默认值
  return new Promise((resolve, reject) => {
    let promise;
    if (type.toUpperCase() === 'GET') {
      promise = axios({
        url,
        params,
      });
    } else if (type.toUpperCase() === 'POST') {
      promise = axios({
        method: 'POST',
        url,
        data: params,
      });
    }
    // 处理返回
    promise
      .then((res) => {
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
}

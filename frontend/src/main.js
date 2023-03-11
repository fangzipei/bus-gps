import { createApp } from 'vue';
import './style.less';
import { createPinia } from 'pinia';
import { Toast } from 'vant';
import App from './App.vue';
import router from './router';

// Toast
import 'vant/es/toast/style';

const app = createApp(App);
app.use(router);
app.use(createPinia());
app.use(Toast);
app.mount('#app');

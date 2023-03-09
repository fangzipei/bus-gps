import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/home/:type',
    name: 'home',
    title: '首页',
    component: () => import('../pages/Home.vue'),
  },
  {
    path: '/login',
    name: 'login',
    title: '登录',
    component: () => import('../pages/Login.vue'),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

import { createRouter, createWebHistory } from 'vue-router';
import { useStore } from '../stores/user';

let store = null;

const routes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    name: 'login',
    title: '登录',
    component: () => import('../pages/Login.vue'),
  },
  {
    path: '/',
    name: 'menu',
    component: () => import('../layouts/Menu.vue'),
    children: [
      {
        path: '/home',
        name: 'home',
        title: '实时地图',
        component: () => import('../pages/Home.vue'),
      },
      {
        path: '/bus-list',
        name: 'bus-list',
        title: '公交搜索',
        component: () => import('../pages/BusList.vue'),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from) => {
  if (!store) store = useStore();
  if (!store.$state.type && to.name !== 'login') {
    return { name: 'login' };
  }
});

export default router;

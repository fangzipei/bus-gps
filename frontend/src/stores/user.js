import { defineStore } from 'pinia';

export const useStore = defineStore('user', {
  state: () => ({
    type: '',
    tourId: '',
  }),
});

import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import eslint from 'vite-plugin-eslint';
import Components from 'unplugin-vue-components/vite';
import { VantResolver } from 'unplugin-vue-components/resolvers';
import fs from 'fs';
import path from 'path';

export default defineConfig({
  plugins: [
    vue(),
    eslint(),
    Components({
      resolvers: [VantResolver()],
    }),
  ],
  server: {
    https: {
      key: fs.readFileSync(path.join(__dirname, './cert/mkcert+3-key.pem')),
      cert: fs.readFileSync(path.join(__dirname, './cert/mkcert+3.pem')),
    },
    host: '0.0.0.0',
    port: 8080,
    proxy: {
      '/api': {
        target: 'http://localhost:8000',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
  resolve: {
    alias: {
      '@': '/src',
    },
  },
  css: {
    preprocessorOptions: {
      less: {
        math: 'always',
        javascriptEnabled: true,
      },
    },
  },
});

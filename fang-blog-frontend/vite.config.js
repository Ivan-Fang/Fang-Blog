import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// import path from "node:path"

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      "/api": {   // use this proxy if the request contains "/api"
        target: "http://localhost:8080",              // set the ip address of proxy
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, "") // /^/ means regex
      }
    }
  }
})

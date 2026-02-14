// vite.config.js
import { defineConfig } from 'vite'; // Make sure defineConfig is imported
import react from '@vitejs/plugin-react';
import tailwindcss from '@tailwindcss/vite'; // Import the Tailwind CSS Vite plugin

export default defineConfig({
    server: {
    proxy: {
      "/api": "http://localhost:8080/",
    },
  },
  plugins: [
    react(),
    tailwindcss(),
  ],
  optimizeDeps: {
    include: [
      '@mui/material',
      '@mui/system',
      '@emotion/react',
      '@emotion/styled'
    ],
  },
});
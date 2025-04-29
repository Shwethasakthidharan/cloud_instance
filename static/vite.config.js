import { defineConfig } from 'vite';
import { fileURLToPath, URL } from 'node:url';

export default defineConfig({
  // Base public path when served in production
  base: '/',

  // Development server configuration
  server: {
    port: 5176, // Frontend development port
    strictPort: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // Your Spring Boot backend
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
        secure: false
      }
    }
  },

  // Build configuration
  build: {
    outDir: '../resources/static', // Output directory for built files
    emptyOutDir: true, // Clean the output directory before build
    rollupOptions: {
      input: {
        main: fileURLToPath(new URL('./index.html', import.meta.url))
      }
    }
  },

  // Resolve configuration
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
});

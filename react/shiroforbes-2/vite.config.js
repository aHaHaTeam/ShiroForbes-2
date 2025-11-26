import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from "@tailwindcss/vite";
import path from "path";
import {fileURLToPath} from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export default defineConfig({
    plugins: [react(), tailwindcss()],
    resolve: {
        alias: {
            "@": path.resolve(__dirname, "./src"),
        },
    },
    server: {
        host: true,
        port: 80,
        proxy: {
            '/api': {
                target: 'http://host.docker.internal:8080',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, '/api/v2'),
            }
        }
    }
});




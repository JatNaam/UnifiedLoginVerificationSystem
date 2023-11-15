# 统一身份验证系统-前台

基于 Vue 3 和 TypeScript ，使用 Vite进行项目管理。

## 1.环境准备：

node.js 和 npm，版本越新越好

## 2.初始化并运行项目：

进入项目所在目录，执行以下命令：

```
npm install
npm run dev
```

## 3. 本地dev环境重新配置：

打开vite.config.ts修改proxy配置为：

```js
proxy: {
      '/api': {
        target: 'http://localhost:8081/', // 目标地址 --> 服务器地址
        changeOrigin: true, // 允许跨域
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
```

到此即可和后台连通。

import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import { ElMessage } from 'element-plus'

import Login from '../views/login/Login.vue'

const routes: Array<RouteRecordRaw> = [
  { path: '/', redirect: '/login' },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/admin-home',
    name: 'AdminHome',
    component: () => import('../views/home/AdminHome.vue'),
    redirect: '/admin-home/unapproveduser',
    children: [
      { path: 'unapproveduser', component: () => import('../components/UnapprovedUser.vue') },
      { path: 'approveduser', component: () => import('../components/ApprovedUser.vue') }
    ]
  },
  {
    path: '/user-home',
    name: 'UserHome',
    component: () => import('../views/home/UserHome.vue')
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

// @ts-ignore
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem(('token'));
  if (to.name !== 'Login' && token == '') {
    ElMessage.error('请先登录')
    next({ name: 'Login' })
  }
  else next()
})

export default router

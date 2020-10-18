import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login.vue'
import Home from '@/views/home/Home.vue'

import systemManageRoute from "./systemManage/systemManage.js"

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home,
    // redirect:'/basicDataStandardQuery',
    children: [
      // {
      //   path: '/home',
      //   redirect: '/basicDataStandardQuery'
      // },
      ...systemManageRoute
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
]

//vue路由解决Uncaught (in promise) Error: Avoided redundant navigation to current location:
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  next()
})

export default router

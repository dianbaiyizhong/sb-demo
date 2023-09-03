import Vue from 'vue'
import Router from 'vue-router'

import Hello from '@/components/Hello'
import Hi from '@/components/Hi'

Vue.use(Router)
export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello,
    },
    {
      path: '/Hello',
      name: 'Hello',
      component: Hello,
    },
    {
      path: '/hi',
      component: Hi,
      children: [{ path: '/', component: Hi }],
    },
  ],
})

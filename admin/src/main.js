import Vue from 'vue'
import App from './App.vue'
import router from './router'

import axios from 'axios'

Vue.config.productionTip = false
Vue.prototype.$ajax = axios;

/**
 * axios拦截器
 */

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  console.log('请求', config);
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  console.log('返回结果', response);
  return response;
}, function (error) {
  // 对响应错误做点什么
  return Promise.reject(error);
});


new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

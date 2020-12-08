import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import axios from 'axios'
import filter from './filter/filter'

Vue.config.productionTip = false
Vue.prototype.$ajax = axios;

/**
 * axios拦截器
 */
// 添加请求拦截器 这部分axios官网上有
axios.interceptors.request.use(function (config) {
	// 在发送请求之前做些什么
	console.log('请求:', config);
	return config;
}, function (error) {
	// 对请求错误做些什么
	return Promise.reject(error);
});

// 添加相应拦截器
axios.interceptors.response.use(function (response) {
	// 对响应数据做些什么
	console.log('返回结果', response);
	return response;
}, function (error) {
	// 对响应错误做些什么
	return Promise.reject(error);
});


// 全局过滤器
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});


new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')

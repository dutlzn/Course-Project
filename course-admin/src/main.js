import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import axios from 'axios'
import filter from './filter/filter'

Vue.config.productionTip = false
Vue.prototype.$ajax = axios;
// 解决每次ajax请求，对应的sessionId不一致的问题
axios.defaults.withCredentials = true;

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


// 路由登录拦截
router.beforeEach((to, from, next) => {
  // 要不要对meta.loginRequire属性做监控拦截
  if (to.matched.some(function (item) {
    return item.meta.loginRequire
  })) {
    let loginUser = Tool.getLoginUser();
    if (Tool.isEmpty(loginUser)) {
      next('/login');
    } else {
      next();
    }
  } else {
    next();
  }
});


new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')

import '@fortawesome/fontawesome-free/css/all.css' // 确保您正在使用 css-loader
import 'material-design-icons-iconfont/dist/material-design-icons.css' // 确保您正在使用 css-load


import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({
	// 图标配置
	icons: {
		iconfont: 'md' || 'fa',
			'rmb': 'fa fa-rmb',
	},
	
});

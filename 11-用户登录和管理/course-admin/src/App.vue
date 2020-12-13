<template>
  <v-app>
		<router-view></router-view>
  </v-app>
</template>

<script>

export default {
  name: 'App',

  components: {

  },
	
	
	// 打开新页面 自动回滚到顶部
	updated() {
	   window.scroll(0, 0);
	},

  data: () => ({
    //
  }),
};
</script>

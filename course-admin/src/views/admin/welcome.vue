<template>
	<div>
		<h1>您好，欢迎{{ loginUser.name  }}进入控制台.</h1>
	</div>
</template>

<script>
	export default {
		name: 'welcome',
		data: function () {
			return {
				loginUser: {},
			}
		},
		
		mounted: function() {
			let _this = this;
			_this.loginUser = Tool.getLoginUser(); 
		},
		methods: {
			
		}
	}
</script>

<style>
</style>

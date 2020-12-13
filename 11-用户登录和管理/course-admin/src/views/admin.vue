<template>
	<v-app>
		<Navbar />
		<v-content class="mx-4">
			<router-view></router-view>
		</v-content>
	</v-app>
</template>

<script>
	import Navbar from '@/components/navbar'

	export default {
		name: 'App',
		
		mounted: function() {
			let _this = this;
			_this.loginUser = SessionStorage.get("USER");
		},

		components: {
			Navbar
		},

		data: () => ({
			loginUser: {},
		}),
	};
</script>

<style>
</style>

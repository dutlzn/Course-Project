<template>
	<nav>
		<v-app-bar app color="#438EB9">

			<v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

			<v-toolbar-title class="text-uppercase">
				<span class="font-weight-bold">在线视频</span>
				<span>系统</span>
			</v-toolbar-title>

			<!-- text 透明度设置， color 改变按钮内部文字颜色 -->
			<!-- 尽可能多的占用空间 grid -->
			<v-spacer></v-spacer>
			<v-menu offset-y>
				<template v-slot:activator="{ on, attrs }">
					<v-btn text v-bind="attrs" v-on="on" class="font-weight-bold">
						菜单
					</v-btn>
				</template>

				<!-- 			<v-list>
					<v-list-item v-for="(item, i) in links" :key="i" :to="item.route" active-class="border">
						<v-list-item-title>{{item.text}}</v-list-item-title>
					</v-list-item>
				</v-list> -->
			</v-menu>

			<v-btn text @click="logout()">
				<span class="font-weight-bold">退出</span>
				<v-icon>exit_to_app</v-icon>
			</v-btn>
		</v-app-bar>

		<v-navigation-drawer v-model="drawer" app color="#F8F8F8">
			<!-- 对应了菜单先写死 -->
			<v-list>

				<v-list-group prepend-icon="settings" color="blue" no-action="" v-show="hasResources('01')">
					<template v-slot:activator>
						<v-list-item-content>
							<v-list-item-title>系统管理</v-list-item-title>
						</v-list-item-content>
					</template>

					<v-list-item to="/system/user" v-show="hasResources('0101')">
						<v-list-item-content>
							<v-list-item-title>用户管理</v-list-item-title>
						</v-list-item-content>
					</v-list-item>

					<v-list-item to="/system/role" v-show="hasResources('0103')">
						<v-list-item-content>
							<v-list-item-title>角色管理</v-list-item-title>
						</v-list-item-content>
					</v-list-item>

					<v-list-item to="/system/resource" v-show="hasResources('0102')">
						<v-list-item-content>
							<v-list-item-title>资源管理</v-list-item-title>
						</v-list-item-content>
					</v-list-item>




				</v-list-group>


				<v-list-group prepend-icon="apps" color="blue" no-action="" v-show="hasResources('02')">
					<template v-slot:activator>
						<v-list-item-content>
							<v-list-item-title>业务管理</v-list-item-title>
						</v-list-item-content>
					</template>

					<v-list-item to="/business/course" v-show="hasResources('0202')">
						<v-list-item-content>
							<v-list-item-title>课程管理</v-list-item-title>
						</v-list-item-content>
					</v-list-item>

					<v-list-item to="/business/teacher" v-show="hasResources('0203')">
						<v-list-item-content>
							<v-list-item-title>讲师管理</v-list-item-title>
						</v-list-item-content>
					</v-list-item>

					<v-list-item to="/business/category" v-show="hasResources('0201')">
						<v-list-item-content>
							<v-list-item-title>分类管理</v-list-item-title>
						</v-list-item-content>
					</v-list-item>

				</v-list-group>

				<!-- :value="true" 就是展开了 -->
				<v-list-group prepend-icon="folder" color="blue" no-action="" v-show="hasResources('03')">
					<template v-slot:activator>
						<v-list-item-content>
							<v-list-item-title>文件管理</v-list-item-title>
						</v-list-item-content>
					</template>

					<v-list-item to="/file/file" v-show="hasResources('0301')">
						<v-list-item-content>
							<v-list-item-title>文件管理</v-list-item-title>
						</v-list-item-content>
					</v-list-item>

				</v-list-group>




			</v-list>

		</v-navigation-drawer>
	</nav>
</template>

<script>
	export default {
		data() {
			return {
				drawer: true,
				link: '/welcome',
				loginUser: {},
			}
		},

		mounted: function() {
			let _this = this;
			_this.loginUser = Tool.getLoginUser();

			if (!_this.hasResourceRouter(_this.$route.name)) {
				_this.$router.push("/login");
			}
		},

		methods: {
			// 获取权限
			hasResources(id) {
				return Tool.hasResource(id);
			},
			/**
			 * 查找是否有权限
			 * @param router
			 */
			hasResourceRouter(router) {
				let _this = this;
				let resources = Tool.getLoginUser().resources;
				if (Tool.isEmpty(resources)) {
					return false;
				}
				for (let i = 0; i < resources.length; i++) {
					if (router === resources[i].page) {
						return true;
					}
				}
				return false;
			},

			// 退出
			logout() {
				let _this = this;
				Loading.show();
				_this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/user/logout/' + _this.loginUser.token).then((response) => {
					Loading.hide();
					let resp = response.data;
					if (resp.success) {
						Tool.setLoginUser(null);
						_this.$router.push("/login")
					} else {
						Toast.warning(resp.message)
					}
				});
			},
		}
	}
</script>

<style scoped>
</style>

<!-- 小节页面 -->
<template>
	<v-app class="ma-3" app>
		<v-dialog v-model="dialog" persistent max-width="300">
			<v-card>
				<v-card-title>
					表单
				</v-card-title>

				<v-card-text>
					<v-col cols="12">
						<v-text-field label="登陆名" v-if="user.id" disabled v-model="user.loginName"></v-text-field>
						<v-text-field label="登陆名" v-if="!user.id" v-model="user.loginName"></v-text-field>
					</v-col>
					<v-col cols="12">
						<v-text-field label="昵称" v-model="user.name" required></v-text-field>
					</v-col>
					<v-col cols="12">
						<v-text-field v-if="user.id" disabled label="密码" type="password" v-model="user.password"></v-text-field>
						<v-text-field v-if="!user.id" label="密码" type="password" v-model="user.password" required></v-text-field>
					</v-col>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn @click="dialog = false" clas="info">
						取消
					</v-btn>

					<v-btn @click="save()" class="success">
						保存
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>


		<v-dialog v-model="dialogPassword" persistent max-width="300">
			<v-card>
				<v-card-title>
					修改密码
				</v-card-title>

				<v-card-text>
					<v-col cols="12">
						<v-text-field label="密码" type="password" v-model="user.password" required></v-text-field>
					</v-col>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn @click="dialogPassword = false" clas="info">
						取消
					</v-btn>

					<v-btn @click="savePassword()" class="success">
						保存
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>


		<p class="ma-10">

			<v-btn v-show="hasResource('010101')" class="mr-5 mb-5" color="primary" @click="add()">
				<v-icon left small>add</v-icon>
				新增
			</v-btn>

			<v-btn class="mb-5" color="primary" @click="list(1)">
				<v-icon left small>refresh</v-icon>
				刷新
			</v-btn>

		</p>


		<pagination ref="pagination" v-bind:list="list" v-bind:itemCount="5"></pagination>


		<v-container>
			<v-row>
				<v-col cols="12">
					<v-simple-table>
						<thead>
							<tr>
								<th class="primary--text text-h6 text-center">id</th>
								<th class="primary--text text-h6 text-center">登陆名</th>
								<th class="primary--text text-h6 text-center">昵称</th>
								<th class="primary--text text-h6 text-center">密码</th>
								<th class="primary--text  text-h6 text-center">
									操作
								</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="user in users" class="text-center">

								<td>{{user.id}}</td>
								<td>{{user.loginName}}</td>
								<td>{{user.name}}</td>
								<td>{{user.password}}</td>

								<td>
									<v-row align="center" justify="space-around">
										<v-btn v-show="hasResource('010101') " x-small fab @click="edit(user)" class="primary">
											<v-icon>edit</v-icon>
										</v-btn>
										<v-btn v-show="hasResource('010103') " x-small fab @click="editPassword(user)" class="info">
											<v-icon>lock</v-icon>
										</v-btn>

										<v-btn v-show="hasResource('010102') " x-small fab @click="del(user.id)" class="warning">
											<v-icon>delete</v-icon>
										</v-btn>
									</v-row>
								</td>
							</tr>
						</tbody>
					</v-simple-table>
				</v-col>
			</v-row>
		</v-container>

	</v-app>
</template>

<script>
	import Pagination from "../../components/pagination.vue";
	export default {
		name: "system-user",
		components: {
			Pagination
		},

		data: function() {
			return {
				user: {},
				users: [],

				dialog: false,
				dialogPassword: false,
			}
		},

		mounted: function() {
			let _this = this;
			_this.$refs.pagination.size = 5;
			_this.list(1);

		},

		methods: {
			// 获取权限
			hasResource(id){
				return Tool.hasResource(id);
			},
			// 获取所有小节的 数据
			list(page) {
				let _this = this;
				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/list', {
					page: page,
					size: _this.$refs.pagination.size,
				}).then((response) => {
					Loading.hide();
					let resp = response.data;
					_this.users = resp.content.list;
					_this.$refs.pagination.render(page, resp.content.total);

				})
			},
			/**
			 * 新增小节
			 */
			add() {
				let _this = this;
				_this.user = {};
				_this.dialog = true;
			},

			/**
			 * 编辑小节
			 */
			edit(user) {
				let _this = this;
				_this.user = $.extend({}, user);
				_this.dialog = true;
			},

			/**
			 * 保存
			 */
			save() {
				let _this = this;

				// 保存校验
				if (1 != 1 ||
					!Validator.require(_this.user.loginName, "登陆名") ||
					!Validator.length(_this.user.loginName, "登陆名", 1, 50) ||
					!Validator.length(_this.user.name, "昵称", 1, 50) ||
					!Validator.require(_this.user.password, "密码")
				) {
					return;
				}

				_this.user.password = hex_md5(_this.user.password + KEY);

				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/save', _this.user).then((response) => {
					Loading.hide();
					let resp = response.data;
					if (resp.success) {
						_this.dialog = false,
							_this.list(1);
						Toast.success("保存成功！");
					} else {
						Toast.warning(resp.message)
					}
				})
			},

			/**
			 * 删除小节
			 */
			del(id) {
				let _this = this;
				Confirm.show("删除用户后不可恢复，确认删除？", function() {
					Loading.show();
					_this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/user/delete/' + id).then((response) => {
						Loading.hide();
						let resp = response.data;
						if (resp.success) {
							_this.list(1);
							Toast.success("删除成功！");
						}
					})
				});
			},

			/**
			 * 
			 * 修改密码
			 */

			editPassword(user) {
				let _this = this;
				_this.user = $.extend({}, user);
				_this.dialogPassword = true;
			},

			/**
			 * 保存密码
			 */
			savePassword() {
				let _this = this;

				// 保存校验
				if (1 != 1 ||
					!Validator.require(_this.user.loginName, "登陆名") ||
					!Validator.length(_this.user.loginName, "登陆名", 1, 50) ||
					!Validator.length(_this.user.name, "昵称", 1, 50) ||
					!Validator.require(_this.user.password, "密码")
				) {
					return;
				}

				_this.user.password = hex_md5(_this.user.password + KEY);

				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/save-password', _this.user).then((response) => {
					Loading.hide();
					let resp = response.data;
					if (resp.success) {
						_this.dialogPassword = false,
							_this.list(1);
						Toast.success("保存成功！");
					} else {
						Toast.warning(resp.message)
					}
				})
			}
		}
	}
</script>

<style>
</style>

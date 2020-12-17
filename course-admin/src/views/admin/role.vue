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
						<v-text-field label="角色" v-model="role.name" required></v-text-field>
					</v-col>
					<v-col cols="12">
						<v-text-field label="描述" v-model="role.desc" required></v-text-field>
					</v-col>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn @click="dialog = false" class="warning">
						取消
					</v-btn>

					<v-btn @click="save()" class="success">
						保存
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>

		<!-- 角色资源关联配置 -->
		<v-dialog v-model="diglogRoleResource" persistent max-width="500">
			<v-card>
				<v-card-title>
					角色资源关联配置
				</v-card-title>

				<!-- <v-card-text> -->
				<v-treeview selectable :items="items" v-model="selection" selection-type="leaf" return-object></v-treeview>
				<!-- </v-card-text> -->

				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn @click="diglogRoleResource = false" class="info">
						取消
					</v-btn>

					<v-btn @click="saveResource()" class="warning">
						保存
					</v-btn>

				</v-card-actions>
			</v-card>
		</v-dialog>



		<p class="ma-10">

			<v-btn class="mr-5 mb-5" color="primary" @click="add()">
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
								<th class="primary--text text-h6 text-center">角色</th>
								<th class="primary--text text-h6 text-center">描述</th>
								<th class="primary--text  text-h6 text-center">
									操作
								</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="role in roles" class="text-center">

								<td>{{role.id}}</td>
								<td>{{role.name}}</td>
								<td>{{role.desc}}</td>

								<td>
									<v-row align="center" justify="space-around">
										<v-btn x-small fab @click="editResource(role)" class="success">
											<v-icon>lock</v-icon>
										</v-btn>

										<v-btn x-small fab @click="edit(role)" class="primary">
											<v-icon>edit</v-icon>
										</v-btn>

										<v-btn x-small fab @click="del(role.id)" class="warning">
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
		name: "system-role",
		components: {
			Pagination
		},

		data: function() {
			return {
				role: {},
				roles: [],
				resource: {},
				resources: [],
				// json ----> list
				resourceList: [], /// 【{} {} {} {} {} {} 】

				// 资源树
				resourceStr: "",
				items: [],
				dialog: false,
				diglogRoleResource: false,
				selection: [], // 表示已经选择的数据
				// 勾选上的资源id列表
				resourceIds: [],
			}
		},

		mounted: function() {
			let _this = this;
			_this.$refs.pagination.size = 5;
			_this.list(1);
			// 加载资源树
			_this.loadResource();

		},
		watch: {


			selection: {
				// deep: true  // 可以深度检测到对象的属性值的变化
				deep: true,

				handler() {

					// changedIndex 就是发生改变的位置
					// console.log(this.selection.length)
					let _this = this;

				}

			}

		},
		methods: {
			// 获取所有小节的数据
			list(page) {
				let _this = this;
				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/list', {
					page: page,
					size: _this.$refs.pagination.size,
				}).then((response) => {
					Loading.hide();
					let resp = response.data;
					_this.roles = resp.content.list;
					_this.$refs.pagination.render(page, resp.content.total);


				})
			},
			/**
			 * 新增小节
			 */
			add() {
				let _this = this;
				_this.role = {};
				_this.dialog = true;
			},

			/**
			 * 编辑小节
			 */
			edit(role) {
				let _this = this;
				_this.role = $.extend({}, role);
				_this.dialog = true;
			},

			/**
			 * 保存
			 */
			save() {
				let _this = this;

				// 保存校验
				if (1 != 1 ||
					!Validator.require(_this.role.name, "角色") ||
					!Validator.length(_this.role.name, "角色", 1, 50) ||
					!Validator.require(_this.role.desc, "描述") ||
					!Validator.length(_this.role.desc, "描述", 1, 100)
				) {
					return;
				}

				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/save', _this.role).then((response) => {
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
				Confirm.show("删除角色后不可恢复，确认删除？", function() {
					Loading.show();
					_this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/role/delete/' + id).then((response) => {
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
			 * 角色资源关联
			 */
			editResource(role) {
				let _this = this;
				// console.log("当前角色id", role.id);
				// 清空之前选择的
				_this.selection = [];
				_this.role = $.extend({}, role);
				// 获取每个角色的资源
				_this.listRoleResource();
				
				_this.diglogRoleResource = true;
			},
			/**
			 * 加载角色资源关联记录
			 */
			listRoleResource() {
				let _this = this;
				_this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/role/list-resource/' + _this.role.id).then((response) => {
					let resp = response.data;
					let resources = resp.content;
					// 只需要子节点
					let childrenNode = [];
					_this.findAllNodes(resources,childrenNode);
					console.log(childrenNode);
					_this.selection = childrenNode;
				});
			},
			
			/**
			 * 找到所有子节点
			 */
			findAllNodes(resources,childrenNode) {
				let _this = this;
				for(let i = 0;i<_this.items.length;++i) {
					_this.help2(_this.items[i],resources,childrenNode);
				}
			},
			
			help2(item,resources,childrenNode) {
				let _this = this;
				if(!item.children) {
					// 没有孩子
					// 如果有 
					if(resources.find(a => {return a.id == item.id})) {
						childrenNode.push(item);
					}
				} else {
					for(let i = 0;i<item.children.length;++i) {
						_this.help2(item.children[i],resources,childrenNode);
					}
				}
			},


			/**
			 * 资源模态框点击保存
			 */
			saveResource() {
				let _this = this;
				_this.resourceIds = [];

				// 把最底层的子节点插入到资源id数组中
				for (let i = 0; i < _this.selection.length; ++i) {
					_this.resourceIds.push(_this.selection[i].id);
				}

				// js数组对象查找 返回一个对象
				// console.log(_this.selection.find( a => {return a.id == "00"}));
				_this.findAllFathers();
				// console.log(_this.resourceIds);

				_this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/save-resource', {
					id: _this.role.id,
					resourceIds: _this.resourceIds
				}).then((response) => {
					let resp = response.data;
					if (resp.success) {
						_this.diglogRoleResource = false; // 关闭模态框
						Toast.success("保存成功!");
					} else {
						Toast.warning(resp.message);
					}
				});
			},

			/**
			 * 后续遍历所有的节点
			 */
			findAllFathers() {
				let _this = this;
				for (let i = 0; i < _this.items.length; ++i) {
					_this.help1(_this.items[i]);
				}
			},

			help1(item) {
				let _this = this;
				if (!item.children) {
					// 
				} else {
					for (let i = 0; i < item.children.length; ++i) {
						_this.help1(item.children[i]);
					}
					for (let i = 0; i < item.children.length; ++i) {
						if (_this.resourceIds.includes(item.children[i].id)) {
							_this.resourceIds.push(item.id);
							break;
						}
					}
				}
			},

			/**
			 * N叉树后序遍历
			 */
			
			
			/**
			 * 加载资源数
			 */
			loadResource() {
				let _this = this;
				Loading.show();
				_this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/resource/load-tree').then((response) => {
					Loading.hide();
					let resp = response.data;
					_this.resources = resp.content;
					_this.items = _this.resources;
					_this.selection = [];
				})
			}
			
			
		}
	}
</script>

<style>
</style>

<template>

	<v-app class="ma-3">

		<!-- 编辑 新增 模态框 -->
		<v-dialog persistent id="form-modal" max-width="300px" v-model="dialog">

			<v-card>
				<v-card-title class="font-weight-bold">
					分类表单
				</v-card-title>

				<v-card-text>
					<v-container>
						<v-col cols="12">
							<v-text-field v-show="active.name" label="父分类" :value="active.name" disabled></v-text-field>
							<v-text-field v-show="!active.name" label="父分类" value="无" disabled></v-text-field>
							<!-- 				<v-else >
									<v-text-field  label="父分类" :value="active.name" disabled></v-text-field>
							</v-else> -->
						</v-col>

						<v-col cols="12">
							<v-text-field v-model="category.name" label="名称" required></v-text-field>
						</v-col>


						<v-col cols="12">

							<v-text-field v-model="category.sort" label="顺序" required></v-text-field>
						</v-col>

					</v-container>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>

					<v-btn @click="dialog = false" class="info">
						取消
					</v-btn>
					<v-btn @click="save()" class="success">
						保存
					</v-btn>
				</v-card-actions>
			</v-card>


		</v-dialog>



		<div class="display-1 grey--text">分类管理</div>

		<v-container>

			<v-row>

				<v-col cols="12" md="6">

					<p>
						<v-btn large class="mr-10 mb-5" color="primary" @click="add1()">
							<v-icon left>mode_edit</v-icon>
							新增一级
						</v-btn>

						<v-btn large color="primary" @click="all()" class="mb-5">
							<v-icon left>refresh</v-icon>
							刷新
						</v-btn>
					</p>

					<!-- table -->
					<v-simple-table>
						<thead>
							<tr>
								<th class="primary--text text-h6">
									id
								</th>
								<th class="primary--text  text-h6">
									名称
								</th>
								<th class="primary--text  text-h6">
									顺序
								</th>
								<th class="primary--text  text-h6">
									操作
								</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="category in level1" v-on:click="onClickLevel1(category)" :class="{'active': category.id === active.id}">
								<td>{{ category.id }}</td>
								<td>{{ category.name }}</td>
								<td>{{ category.sort }}</td>
								<td>
									<v-row align="center" justify="space-around">
										<v-btn x-small fab color="info" @click="edit(category)">
											<v-icon>edit</v-icon>
										</v-btn>


										<v-btn x-small fab color="warning"  @click="del(category.id)">
											<v-icon>delete</v-icon>
										</v-btn>
									</v-row>

								</td>
							</tr>
						</tbody>
					</v-simple-table>


				</v-col>
				<v-col cols="12" md="6">
					<p>
						<v-btn large color="primary" @click="add2()" class="mb-5">
							<v-icon left>edit</v-icon>
							新增二级
						</v-btn>
					</p>

					<v-simple-table>
						<thead>
							<tr>
								<th class="primary--text  text-h6">id</th>
								<th class="primary--text  text-h6"> 名称</th>
								<th class="primary--text  text-h6">顺序</th>
								<th class="primary--text  text-h6">操作</th>
							</tr>
						</thead>

						<tbody>
							<tr v-for="category in level2">
								<td>{{ category.id }}</td>
								<td>{{ category.name }}</td>
								<td>{{ category.sort }}</td>
								<td>
									<v-row align="center" justify="space-around">
										<v-btn x-small fab color="info" @click="edit(category
										)">
											<v-icon>edit</v-icon>
										</v-btn>
										<v-btn x-small fab color="warning" @click="del(category.id)">
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
	export default {
		components: {},
		name: "business-category",
		data: function() {
			return {
				category: {},
				categorys: [],
				level1: [],
				level2: [],
				active: {},
				dialog: false,

			}
		},

		mounted: function() {
			let _this = this;
			_this.all();
		},

		methods: {
			/**
			 * 新增一级
			 */
			add1() {
				let _this = this;
				_this.category = {
					parent: "00000000"
				};
				_this.level2 = [];
				_this.active = {};

				_this.dialog = true;
			},
			/**
			 * 列表查询,查询全部
			 */
			all() {
				let _this = this;
				// _this.active = {};
				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/category/all")
					.then((response) => {
						Loading.hide();
						let resp = response.data;
						_this.categorys = resp.content;
						// 将所有记录格式化成树形结构
						_this.level1 = [];
						for (let i = 0; i < _this.categorys.length; i++) {
							let c = _this.categorys[i];
							if (c.parent === "00000000") {
								_this.level1.push(c);
								for (let j = 0; j < _this.categorys.length; j++) {
									let child = _this.categorys[j];
									if (child.parent === c.id) {
										if (Tool.isEmpty(c.children)) {
											c.children = [];
										}
										c.children.push(child);
									}
								}
							}
						}

						_this.level2 = [];
						// 对当前一级分类中选中的表格触发一次点击事件，以刷新二级菜单列表
						// 注意：界面的渲染需要等vue绑定好变量后才做，所以加延时100ms
						setTimeout(function() {
							$("tr.active").trigger("click");
						}, 100);
					})
			},

			/**
			 * 一级分类吗某一行的点击触发事件
			 */
			onClickLevel1(category) {
				let _this = this;
				_this.active = category;
				_this.level2 = category.children;
			},

			/**
			 * 模态框 表单 保存
			 */

			save() {

				let _this = this;
				// 保存校验
				// 保存校验
				if (
					1 != 1 ||
					!Validator.require(_this.category.parent, "父id") ||
					!Validator.require(_this.category.name, "名称") ||
					!Validator.length(_this.category.name, "名称", 1, 50)
				) {
					return;
				}

				Loading.show();
				_this.$ajax
					.post(
						process.env.VUE_APP_SERVER + "/business/admin/category/save",
						_this.category
					)
					.then((response) => {
						Loading.hide();
						let resp = response.data;
						if (resp.success) {
							this.dialog = false;
							_this.all();
							Toast.success("保存成功！");
						} else {
							Toast.warning(resp.message);
						}
					});


			},

			/**
			 * 编辑
			 */
			edit(category) {
				let _this = this;
				_this.category = $.extend({}, category);
				_this.dialog = true;
			},

			/**
			 * 增加二级分类
			 */
			add2() {
				let _this = this;
				// 判断有没有选中一级分类
				if (Tool.isEmpty(_this.active)) {
					Toast.warning("请先选择一级分类");
					return;
				}

				_this.category = {
					parent: _this.active.id
				}
				
				_this.dialog = true;

			},
			
			
			/**
			 * 根据id删除
			 */
			del(id) {
				let _this = this;
				Confirm.show("删除分类后不可恢复，确认删除?", function() {
					Loading.show();
					_this.$ajax
						.delete(
							process.env.VUE_APP_SERVER + "/business/admin/category/delete/" + id
						).then( (response) => {
							Loading.hide();
							let resp = response.data;
							if (resp.success) {
								_this.all();
								Toast.success("删除成功！");
							}
						})
				});
			}
			
			
		}

	};
</script>


<style scoped>
	.active td {
		background-color: yellowgreen !important;
	}
/* 
	/deep/ .v-input .v-label {
		font-size: 20px;
		font-weight: bold;
		color: black;
	}

	/deep/ .v-input input {
		font-size: 20px;
		color: black;
	} */
</style>

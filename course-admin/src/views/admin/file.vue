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
						<v-text-field label="相对路径" v-model="file.path" required></v-text-field>
					</v-col>
					<v-col cols="12">
						<v-text-field label="文件名" v-model="file.name" required></v-text-field>
					</v-col>
					<v-col cols="12">
						<v-text-field label="后缀" v-model="file.suffix" required></v-text-field>
					</v-col>
					<v-col cols="12">
						<v-text-field label="大小" v-model="file.size" required></v-text-field>
					</v-col>
					<v-col cols="12">
						<v-select label="用途" v-model="file.use" :items="FILE_USE" item-text="value" item-value="key"></v-select>
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


		<p class="ma-10">

<!-- 			<v-btn class="mr-5 mb-5" color="primary" @click="add()">
				<v-icon left small>add</v-icon>
				新增
			</v-btn>
 -->
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
								<th class="primary--text text-h6 text-center">相对路径</th>
								<th class="primary--text text-h6 text-center">文件名</th>
								<th class="primary--text text-h6 text-center">后缀</th>
								<th class="primary--text text-h6 text-center">大小</th>
								<th class="primary--text text-h6 text-center">用途</th>
				<!-- 				<th class="primary--text  text-h6 text-center">
									操作
								</th> -->
							</tr>
						</thead>
						<tbody>
							<tr v-for="file in files" class="text-center">

								<td>{{file.id}}</td>
								<td>{{file.path}}</td>
								<td>{{file.name}}</td>
								<td>{{file.suffix}}</td>
								<td>{{file.size}}</td>
								<td>{{FILE_USE | optionKV(file.use)}}</td>
<!-- 
								<td>
									<v-row align="center" justify="space-around">
										<v-btn x-small fab @click="edit(file)" class="primary">
											<v-icon>edit</v-icon>
										</v-btn>

										<v-btn x-small fab @click="del(file.id)" class="warning">
											<v-icon>delete</v-icon>
										</v-btn>
									</v-row>
								</td> -->
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
		name: "file-file",
		components: {
			Pagination
		},

		data: function() {
			return {
				file: {},
				files: [],
				FILE_USE: FILE_USE_ARRAY,

				dialog: false,
			}
		},

		mounted: function() {
			let _this = this;
			_this.$refs.pagination.size = 5;
			_this.list(1);

		},

		methods: {
			// 获取所有小节的数据
			list(page) {
				let _this = this;
				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/file/list', {
					page: page,
					size: _this.$refs.pagination.size,
				}).then((response) => {
					Loading.hide();
					let resp = response.data;
					_this.files = resp.content.list;
					_this.$refs.pagination.render(page, resp.content.total);

				})
			},
			/**
			 * 新增小节
			 */
			add() {
				let _this = this;
				_this.file = {};
				_this.dialog = true;
			},

			/**
			 * 编辑小节
			 */
			edit(file) {
				let _this = this;
				_this.file = $.extend({}, file);
				_this.dialog = true;
			},

			/**
			 * 保存
			 */
			save() {
				let _this = this;

				// 保存校验
				if (1 != 1 ||
					!Validator.require(_this.file.path, "相对路径") ||
					!Validator.length(_this.file.path, "相对路径", 1, 100) ||
					!Validator.length(_this.file.name, "文件名", 1, 100) ||
					!Validator.length(_this.file.suffix, "后缀", 1, 10)
				) {
					return;
				}

				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/file/save', _this.file).then((response) => {
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
				Confirm.show("删除文件后不可恢复，确认删除？", function() {
					Loading.show();
					_this.$ajax.delete(process.env.VUE_APP_SERVER + '/file/admin/file/delete/' + id).then((response) => {
						Loading.hide();
						let resp = response.data;
						if (resp.success) {
							_this.list(1);
							Toast.success("删除成功！");
						}
					})
				});
			}
		}
	}
</script>

<style>
</style>

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
						<v-text-field label="名称" v-model="resource.name" required></v-text-field>
					</v-col>
					<v-col cols="12">
						<v-text-field label="页面" v-model="resource.page" required></v-text-field>
					</v-col>
					<v-col cols="12">
						<v-text-field label="请求" v-model="resource.request" required></v-text-field>
					</v-col>
					<v-col cols="12">
						<v-text-field label="父id" v-model="resource.parent" required></v-text-field>
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

			<!-- 		<v-btn class="mr-5 mb-5" color="primary" @click="add()">
				<v-icon left small>add</v-icon>
				新增
			</v-btn> -->
			
			<!-- 资源树的编辑 -->
			<!-- <v-text-field  outlined></v-text-field> --> 
			<v-row>
				<v-col cols="10">
					<v-textarea outlined name="input-7-1" label="资源树"  v-model="resourceStr" hint="输入资源配置信息"></v-textarea>
				</v-col>
				
				<v-col  cols="2" class="d-flex ">
					<v-btn class="my-auto" color="primary" @click="save()">
						保存
					</v-btn>
				</v-col>
			</v-row>
			<br>

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
								<th class="primary--text text-h6 text-center">名称</th>
								<th class="primary--text text-h6 text-center">页面</th>
								<th class="primary--text text-h6 text-center">请求</th>
								<th class="primary--text text-h6 text-center">父id</th>
								<th class="primary--text  text-h6 text-center">
									操作
								</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="resource in resources" class="text-center">

								<td>{{resource.id}}</td>
								<td>{{resource.name}}</td>
								<td>{{resource.page}}</td>
								<td>{{resource.request}}</td>
								<td>{{resource.parent}}</td>

								<td>
									<v-row align="center" justify="space-around">
										<v-btn x-small fab @click="edit(resource)" class="primary">
											<v-icon>edit</v-icon>
										</v-btn>

										<v-btn x-small fab @click="del(resource.id)" class="warning">
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
		name: "system-resource",
		components: {
			Pagination
		},

		data: function() {
			return {
				
				resource: {},
				resources: [],
				// 资源树
				resourceStr: "",

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
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/resource/list', {
					page: page,
					size: _this.$refs.pagination.size,
				}).then((response) => {
					Loading.hide();
					let resp = response.data;
					_this.resources = resp.content.list;
					_this.$refs.pagination.render(page, resp.content.total);

				})
			},
			
			/**
			 * 保存
			 */
     /**
       * 点击【保存】
       */
      save() {
        let _this = this;

        // 保存校验
        if (Tool.isEmpty(_this.resourceStr)) {
          Toast.warning("资源不能为空！");
          return;
        }
        let json = JSON.parse(_this.resourceStr);

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/resource/save', json).then((response)=>{
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            $("#form-modal").modal("hide");
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
				Confirm.show("删除资源后不可恢复，确认删除？", function() {
					Loading.show();
					_this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/resource/delete/' + id).then((response) => {
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

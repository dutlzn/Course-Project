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
					_this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/list', {
						page: page,
						size: _this.$refs.pagination.size,
					}).then((response)=>{
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
        if (1 != 1
          || !Validator.require(_this.role.name, "角色")
          || !Validator.length(_this.role.name, "角色", 1, 50)
          || !Validator.require(_this.role.desc, "描述")
          || !Validator.length(_this.role.desc, "描述", 1, 100)
        ) {
          return;
        }

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/save', _this.role).then((response)=>{
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
        Confirm.show("删除角色后不可恢复，确认删除？", function () {
          Loading.show();
          _this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/role/delete/' + id).then((response)=>{
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

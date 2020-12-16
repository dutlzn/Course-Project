<!-- 小节页面 -->
<template>
	<v-app class="ma-3" app>

		<p class="ma-10">

			<v-btn class="mb-5" color="primary" @click="list(1)">
				<v-icon left small>refresh</v-icon>
				刷新
			</v-btn>

			<v-row>


				<!-- 				<v-col cols="2">
					<v-btn class="my-auto" color="primary" @click="save()">
						保存
					</v-btn>
				</v-col> -->
				<v-col cols="6">
					<!-- <p class="display-1">资源树</p> -->
					<v-treeview open-all :items="items" return-object></v-treeview>
				</v-col>

				<v-col cols="6">
					<v-textarea outlined name="input-7-1" label="资源树" v-model="resourceStr" hint="输入资源配置信息"></v-textarea>
					<v-btn class="my-auto" color="primary" @click="save()">
						保存
					</v-btn>
				</v-col>
			</v-row>
			<br>

		</p>



	</v-app>

</template>

<script>
	import tree from "vue-giant-tree";
	
	export default {
		name: "system-resource",
		components: {
			// Pagination
			tree
		},

		data: function() {
			return {

				resource: {},
				resources: [],
				// 资源树
				resourceStr: "",
				items: [],

												
			}
		},

		mounted: function() {
			let _this = this;
			_this.list();
			

		},

		methods: {


			// 获取所有小节的数据
			list() {
				let _this = this;
				Loading.show();
				_this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/resource/load-tree').then((response) => {
					Loading.hide();
					let resp = response.data;
					_this.resources = resp.content;
					_this.items = _this.resources;
		
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
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/resource/save', json).then((response) => {
					Loading.hide();
					let resp = response.data;
					if (resp.success) {
						_this.list();
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

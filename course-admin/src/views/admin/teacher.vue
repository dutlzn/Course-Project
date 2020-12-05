<template>
	<v-app class="ma-3">


		<v-dialog persistent id="form-modal" max-width="500px" v-model="dialog">
			<v-card>
				<v-card-title class="font-weight-bold">
					教师表单
				</v-card-title>
				<v-card-text>


					<v-row>
						<v-col cols="6">
							<v-text-field label="姓名" v-model="teacher.name"></v-text-field>
						</v-col>

						<v-col cols="6">
							<v-text-field label="昵称" v-model="teacher.nickname"></v-text-field>
						</v-col>
					</v-row>

					<v-col cols="12">

						<file v-bind:text="'上传头像'" v-bind:after-upload="afterUpload" v-bind:suffixs="['jpg', 'jpeg', 'png']"
						 v-bind:input-id="'image-upload'" v-bind:label="'头像'"></file>

					</v-col>



					<!-- <div v-show="teacher.image"> -->
					<v-col cols="4">
						<v-img v-show="teacher.image" v-bind:src="teacher.image"></v-img>
						<v-img v-show="!teacher.image" src="/static/image/avatar.png"></v-img>
					</v-col>
					<!-- </div> -->


					<v-row>
						<v-col cols="6">
							<v-text-field label="职位" v-model="teacher.position"></v-text-field>
						</v-col>

						<v-col cols="6">
							<v-text-field label="座右铭" v-model="teacher.motto"></v-text-field>
						</v-col>

					</v-row>
					<v-col cols="12">
						<v-textarea outlined label="简介" v-model="teacher.intro"></v-textarea>
					</v-col>


				</v-card-text>

				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn @click="dialog = false" class="info">
						取消
					</v-btn>

					<v-btn @click="save()" class="success">
						确认
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>

		<!-- 主要内容 -->
		<div class="display-1 grey--text">讲师管理</div>

		<p class="ma-10">
			<v-btn class="mr-5 mb-5" color="primary" @click="add()">
				<v-icon left small>edit</v-icon>
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
				<v-col sm="12" md="3" v-for="(teacher, i) in teachers" :key="i">
					<v-card>
						<v-img class="card-img" v-show="!teacher.image" src="/static/image/avatar.png" v-bind:title="teacher.intro">

						</v-img>

						<v-img class="card-img" v-show="teacher.image" :src="teacher.image" v-bind:title="teacher.intro">

						</v-img>

						<v-card-title align="center" class="font-weight-bold">
							<v-row>
								<v-col>
									{{ teacher.position }}
								</v-col>
							</v-row>
						</v-card-title>

						<v-card-text align="center">
							<v-icon color="primary">person</v-icon>

							<span class="font-weight-bold subtitle-1">{{ teacher.name }} </span>
							<span>【{{teacher.nickname}}】</span>
						</v-card-text>

						<v-card-actions>

							<v-spacer></v-spacer>
							<v-btn color="primary" @click="edit(teacher)">
								<v-icon>edit</v-icon>
								编辑
							</v-btn>
							<v-btn color="error" @click="del(teacher.id)">
								<v-icon>delete</v-icon>
								删除
							</v-btn>
							<v-spacer></v-spacer>
						</v-card-actions>
					</v-card>
				</v-col>
			</v-row>
		</v-container>

	</v-app>
</template>

<script>
	import Pagination from "../../components/pagination";
	import File from "../../components/file.vue";
	export default {
		name: 'business-teacher',

		components: {
			Pagination,
			File
		},

		data: function() {
			return {
				teacher: {},
				teachers: [],

				// 控制模态框
				dialog: false,
			}
		},

		mounted: function() {
			let _this = this;
			_this.$refs.pagination.size = 5;
			_this.list(1);
		},

		methods: {
			/** 
			 * 列表查询
			 */
			list(page) {
				let _this = this;
				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/list', {
					page: page,
					size: _this.$refs.pagination.size,
				}).then((response) => {
					let resp = response.data;
					_this.teachers = resp.content.list;
					_this.$refs.pagination.render(page, resp.content.total);
					Loading.hide();

				})
			},

			/**
			 * 修改教师
			 */

			edit(teacher) {
				let _this = this;
				_this.teacher = $.extend({}, teacher);
				_this.dialog = true;
			},

			/**
			 * 删除教师，By Id
			 */
			del(id) {
				let _this = this;
				Confirm.show("删除讲师后不可恢复，确认删除？", function() {
					Loading.show();
					_this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/teacher/delete/' + id).then((response) => {
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
			 * 保存教师
			 */
			save() {
				let _this = this;

				// 保存校验
				if (1 != 1 ||
					!Validator.require(_this.teacher.name, "姓名") ||
					!Validator.length(_this.teacher.name, "姓名", 1, 50) ||
					!Validator.length(_this.teacher.nickname, "昵称", 1, 50) ||
					!Validator.length(_this.teacher.image, "头像", 1, 100) ||
					!Validator.length(_this.teacher.position, "职位", 1, 50) ||
					!Validator.length(_this.teacher.motto, "座右铭", 1, 50) ||
					!Validator.length(_this.teacher.intro, "简介", 1, 500)
				) {
					return;
				}

				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/save', _this.teacher).then((response) => {
					Loading.hide();
					let resp = response.data;
					if (resp.success) {
						_this.list(1);
						_this.dialog = false;
						Toast.success("保存成功！");
					} else {
						Toast.warning(resp.message)
					}
				})
			},


			/**
			 * 新增教师
			 */
			add() {
				let _this = this;
				_this.teacher = {
					image: "/static/image/avatar.png",
				};
				_this.dialog = true;
			},


			/**
			 * 选择头像之后
			 */
			afterUpload(resp) {
				let _this = this;
				let image = resp.content;
				_this.teacher.image = image;
			}
		}

	}
</script>

<style scoped>
	/* 鼠标移到图片上变手  */
	.card-img {
		cursor: pointer;
	}
</style>

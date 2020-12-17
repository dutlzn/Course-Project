<!-- 大章页面 -->
<template>
	<v-app class="ma-3" app>
		<!-- 主要内容 -->
		<div class="display-1 grey--text">大章管理
			<v-icon>keyboard_arrow_right</v-icon>
			<span class="text-h5">{{ course.name }}</span>
		</div>



		<v-dialog v-model="dialogChapter" persistent max-width="300">
			<v-card>
				<v-card-title>
					大章表单
				</v-card-title>

				<v-card-text>
					<v-text-field label="课程名称" :value="course.name" disabled></v-text-field>
				</v-card-text>

				<v-card-text>
					<v-text-field label="大章名称" v-model="chapter.name" required></v-text-field>
				</v-card-text>

				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn @click="dialogChapter = false" clas="info">
						取消
					</v-btn>

					<v-btn @click="save()" class="success">
						保存
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>


		<p class="ma-10">
			<v-btn to="/business/course" class="mr-5 mb-5" color="primary">
				返回课程
			</v-btn>
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
							<!-- <v-col align="center"> -->
							<tr>
								<th class="primary--text  text-h6 text-center">
									ID
								</th>
								<th class="primary--text  text-h6 text-center">
									名称
								</th>
								<th class="primary--text  text-h6 text-center">
									操作
								</th>
							</tr>
							<!-- </v-col> -->
						</thead>
						<tbody>
							<tr v-for="chapter in chapters">
								<td class="text-center">{{ chapter.id }}</td>
								<td class="text-center">{{ chapter.name }}</td>
								<td>
									<v-row align="center" justify="space-around">
										<v-btn  x-small fab color="primary" @click="toSection(chapter)">
											<v-icon>redo</v-icon>
										</v-btn>
										<v-btn x-small fab color="info" @click="edit(chapter)">
											<v-icon>edit</v-icon>
										</v-btn>
										<v-btn x-small fab color="warning" @click="del(chapter.id)">
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
		name: 'business-chapter',
		components: {
			Pagination
		},
		data: function() {
			return {
				// 映射表单数据
				chapter: {},
				chapters: [],
				course: {},
				//模态框
				dialogChapter: false,
			}
		},

		mounted: function() {
			let _this = this;
			_this.$refs.pagination.size = 5;
			let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
			if (Tool.isEmpty(course)) {
				_this.$router.push("/welcome");
			}
			_this.course = course;
			_this.list(1);
		},

		methods: {

			// 获取所有大章的数据
			list(page) {
				let _this = this;
				Loading.show();
				_this.$ajax
					.post(process.env.VUE_APP_SERVER + "/business/admin/chapter/list", {
						page: page,
						//  跟组子组件获取  size应该有一个默认的参数
						size: _this.$refs.pagination.size,
						courseId: _this.course.id,
					})
					.then((response) => {
						Loading.hide();
						let resp = response.data;
						_this.chapters = resp.content.list;
						_this.$refs.pagination.render(page, resp.content.total);
					});
			},

			/**
			 * 删除大章
			 */
			// 删除大章
			del(id) {
				let _this = this;

				Confirm.show("删除大章后不可恢复，确认删除？", function() {
					Loading.show();
					_this.$ajax
						.delete(
							process.env.VUE_APP_SERVER + "/business/admin/chapter/delete/" + id
						)
						.then((response) => {
							Loading.hide();
							let resp = response.data;
							if (resp.success) {
								_this.list(1);
								Toast.success("删除成功！");
							}
						});
				});
			},


			/**
			 * 新增大章
			 */
			add() {
				let _this = this;
				_this.chapter = {};
				_this.dialogChapter = true;
			},


			/**
			 * 保存大章
			 */
			// 保存大章的数据
			save() {
				let _this = this;
				// 保存校验 非空检验和长度检验
				if (
					!Validator.require(_this.chapter.name, "名称") ||
					// !Validator.require(_this.chapter.courseId, '课程ID') ||
					!Validator.length(_this.chapter.courseId, "课程ID", 1, 8)
				) {
					return;
				}

				_this.chapter.courseId = _this.course.id;

				Loading.show();
				_this.$ajax
					.post(
						process.env.VUE_APP_SERVER + "/business/admin/chapter/save",
						_this.chapter
					)
					.then((response) => {
						Loading.hide();
						let resp = response.data;
						if (resp.success) {
							// 关闭模态框
							_this.dialogChapter = false;
							_this.list(1);
							Toast.success("保存成功");
						} else {
							Toast.warning(resp.message);
						}
					});
			},

			/**
			 * 编辑大章
			 */
			edit(chapter) {
				let _this = this;
				_this.chapter = $.extend({}, chapter);
				_this.dialogChapter = true;
			},


			/**
			 * 点击【小节】
			 */
			toSection(chapter) {
				let _this = this;
				SessionStorage.set(SESSION_KEY_CHAPTER, chapter);
				_this.$router.push("/business/section");
			},

		}
	}
</script>

<style>


</style>

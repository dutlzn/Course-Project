<!-- 小节页面 -->
<template>
	<v-app class="ma-3" app>
		<!-- 主要内容 -->
		<div class="display-1 grey--text">
			{{ course.name }}
			<v-icon>keyboard_arrow_right</v-icon>
			<span class="text-h5 grey--text"> {{ chapter.name }}</span>
		</div>


		<v-dialog v-model="dialogSection" persistent max-width="300">
			<v-card>
				<v-card-title>
					小节表单
				</v-card-title>

				<v-card-text>
					<v-row>
						<v-col cols="12">
							<v-text-field label="课程名称" :value="course.name" disabled=""></v-text-field>
						</v-col>
						<v-col cols="12">
							<v-text-field label="大章名称" :value="chapter.name" disabled></v-text-field>
						</v-col>

						<v-col cols="12">
							<v-text-field label="标题" v-model="section.title" required></v-text-field>
						</v-col>

						<v-col cols="4">
							<v-select label="收费" v-model="section.charge" :items="SECTION_CHARGE" item-text="value" item-value="key"></v-select>
						</v-col>
						<v-col cols="12">
							<v-text-field label="视频" v-model="section.video" required></v-text-field>
						</v-col>

						<v-col cols="12">
							<v-text-field label="时长" v-model="section.time" required></v-text-field>
						</v-col>
						<v-col cols="12">
							<v-text-field label="顺序" v-model="section.sort" required></v-text-field>
						</v-col>

					</v-row>
				</v-card-text>

				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn @click="dialogSection = false" clas="info">
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

			<v-btn to="/business/chapter" class="mr-5 mb-5" color="primary">
				返回大章
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
							<tr>
								<th class="primary--text text-h6 text-center">
									ID
								</th>
								<th class="primary--text  text-h6 text-center">
									标题
								</th>
								<th class="primary--text  text-h6 text-center">
									视频
								</th>
								<th class="primary--text  text-h6 text-center">
									时长
								</th>
								<th class="primary--text  text-h6 text-center">
									收费
								</th>
								<th class="primary--text  text-h6 text-center">
									顺序
								</th>
								<th class="primary--text  text-h6 text-center">
									操作
								</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="section in sections" class="text-center">
								<td class="text-center">
									{{ section.id}}
								</td>

								<td class="text-center">
									{{ section.title }}
								</td>

								<td>
									{{ section.video }}
								</td>

								<td>
									{{ section.time | formatSecond }}
								</td>

								<td>
									{{ SECTION_CHARGE | optionKV(section.charge) }}
								</td>

								<td>
									{{ section.sort }}
								</td>

								<td>
									<v-row align="center" justify="space-around">
										<v-btn x-small fab @click="edit(section)" class="primary">
											<v-icon>edit</v-icon>
										</v-btn>

										<v-btn x-small fab @click="del(section.id)" class="warning">
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

		components: {
			Pagination
		},

		data: function() {
			return {
				section: {},
				sections: [],
				chapter: {},
				course: {},
				SECTION_CHARGE: SECTION_CHARGE_ARRAY,

				dialogSection: false,
			}
		},

		mounted: function() {
			let _this = this;
			_this.$refs.pagination.size = 5;
			let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
			let chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};
			if (Tool.isEmpty(course) || Tool.isEmpty(chapter)) {
				_this.$router.push("/welcome");
			}
			_this.course = course;
			_this.chapter = chapter;
			_this.list(1);
		},

		methods: {
			// 获取所有小节的数据
			list(page) {
				let _this = this;
				Loading.show();
				_this.$ajax
					.post(process.env.VUE_APP_SERVER + "/business/admin/section/list", {
						page: page,
						//  跟组子组件获取  size应该有一个默认的参数
						size: _this.$refs.pagination.size,
						courseId: _this.course.id,
						chapterId: _this.chapter.id
					})
					.then((response) => {
						Loading.hide();
						let resp = response.data;
						_this.sections = resp.content.list;
						_this.$refs.pagination.render(page, resp.content.total);
					});
			},

			/**
			 * 新增小节
			 */
			add() {
				let _this = this;
				_this.dialogSection = true;
			},

			/**
			 * 编辑小节
			 */
			edit(section) {
				let _this = this;
				_this.section = $.extend({}, section);
				_this.dialogSection = true;
			},

			/**
			 * 保存
			 */
			save() {
				let _this = this;
				// 保存校验
				if (
					1 != 1 ||
					!Validator.require(_this.section.title, "标题") ||
					!Validator.length(_this.section.title, "标题", 1, 50) ||
					!Validator.length(_this.section.video, "视频", 1, 200)
				) {
					return;
				}

				_this.section.courseId = _this.course.id;
				_this.section.chapterId = _this.chapter.id;

				Loading.show();
				_this.$ajax
					.post(
						process.env.VUE_APP_SERVER + "/business/admin/section/save",
						_this.section
					)
					.then((response) => {
						Loading.hide();
						let resp = response.data;
						if (resp.success) {
							// 关闭模态框
							_this.dialogSection = false;
							_this.list(1);
							Toast.success("保存成功");
						} else {
							Toast.warning(resp.message);
						}
					});
			},

			/**
			 * 删除小节
			 */
			del(id) {
				let _this = this;

				Confirm.show("删除小节后不可恢复，确认删除？", function() {
					Loading.show();
					_this.$ajax
						.delete(
							process.env.VUE_APP_SERVER + "/business/admin/section/delete/" + id
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
		}
	}
</script>

<style>
</style>

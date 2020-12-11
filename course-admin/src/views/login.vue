<template>
	<v-app>
		<v-content>
			<v-container class="fill-height" fluid>
				<v-row align="center" justify="center">
					<v-col col="12" sm="10" md="4">
						<v-card class="elevation-12">
							<v-window v-model="step">
								<v-window-item :value="1">
									<v-row>
										<v-col cols="12" md="12">
											<v-card-text class="mt-12">
												<h1 class="text-center display-2 blue--text text--accent-3">
													登录页面
												</h1>
												<v-container>
													<v-text-field autocomplete="off" v-model="user.loginName" label="用户名" name="name" prepend-icon="person" type="text" color="blue accent-3" />

													<v-text-field  autocomplete="off"  v-model="user.password" id="password" label="密码" name="Password" prepend-icon="lock" type="password"
													 color="blue accent-3" />
													<v-row class="d-flex align-center">
												
														<v-col cols="9">
															<v-text-field v-model="user.imageCode" id="验证码" label="验证码" name="imgCode" prepend-icon="done"></v-text-field>
														</v-col>
														
														<v-col cols="3" class="d-flex align-center jusify-center">
															<img @click="loadImageCode" id="image-code" alt="验证码" />
														</v-col>
														
													</v-row>
												</v-container>

												<v-checkbox label="记住我" v-model="remember"></v-checkbox>

												<!-- 				<h3 class="text-center mt-3">
													忘记密码
												</h3>
 -->
											</v-card-text>

											<div class="text-center mt-3">
												<v-btn rounded color="blue accent-3" dark v-on:click="login()">登录</v-btn>
											</div>
										</v-col>

								<!-- 		<v-col cols="12" md="4" class="green accent-3">
											<v-card-text class="blue-grey--text mt-12">
												<h1 class="text-center display-1">你好，朋友!</h1>
												<h5 class="text-center">输入您的个人详细信息，然后开始与我们一起旅行。</h5>
											</v-card-text>
											<div class="text-center">
												<v-btn rounded outlined="" dark v-on:click="change1()">
													注册
												</v-btn>
											</div>
										</v-col> -->
									</v-row>
								</v-window-item>
								<v-window-item :value="2">
									<v-row class="fill-height">
										<v-col cols="12" md="4" class="teal accent-3">
											<v-card-text class="white--text mt-12">
												<h1 class="text-center display-1">欢迎回来!</h1>
												<h5 class="text-center">请将您的个人信息与我们保持联系</h5>
											</v-card-text>
											<div class="text-center ">
												<v-btn rounded outlined dark v-on:click="change2()">
													登录
												</v-btn>
											</div>


										</v-col>
										<v-col cols="12" md="8">
											<v-card-text class="mt-12">
												<h1 class="text-center display-2 teal--text text--accent-3">
													创建账号
												</h1>

												<v-form>
													<v-text-field label="Name" name="name" prepend-icon="person" type="text" color="teal accent-3" />

													<v-text-field label="Email" name="Email" prepend-icon="email" type="text" color="teal accent-3" />

													<v-text-field id="password" label="Password" name="Password" prepend-icon="lock" type="password" color="teal accent-3" />

												</v-form>



											</v-card-text>

											<div class="text-center mt-5">
												<v-btn rounded color="teal accent-3" dark>
													注册
												</v-btn>
											</div>
										</v-col>
									</v-row>
								</v-window-item>
							</v-window>
						</v-card>
					</v-col>
				</v-row>
			</v-container>
		</v-content>
	</v-app>
</template>

<script>
	export default {
		name: 'login',
		data: () => ({
			user: {},
			step: 1,
			remember: true,
			imageCodeToken: ""
		}),

		props: {
			source: String
		},

		mounted: function() {
			let _this = this;
			let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
			if (rememberUser) {
				_this.user = rememberUser;
			}

			// 初始时加载一次验证码图片
			_this.loadImageCode();
		},

		methods: {
			change1() {
				this.step++;
			},

			change2() {
				this.step--;
			},

			login() {
				let _this = this;


				if (
					1 != 1 ||
					!Validator.require(_this.user.loginName, "用户名") ||
					!Validator.require(_this.user.password, "密码")
				) {
					return;
				}

				// let passwordShow = _this.user.password;
				// 如果密码是从缓存带出来的，则不需要重新加密
				let md5 = hex_md5(_this.user.password);
				let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER) || {};
				if (md5 !== rememberUser.md5) {
					_this.user.password = hex_md5(_this.user.password + KEY);
				}



				// 进行第一次加密
				// _this.user.password = hex_md5(_this.user.password + KEY);
				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/login', _this.user).then(
					(response) => {
						Loading.hide();
						let resp = response.data;
						let loginUser = resp.content;
						if (resp.success) {

							// console.log(resp.content);
							// SessionStorage.set("USER", resp.content);
							Tool.setLoginUser(resp.content);
							if (_this.remember) {
								// 如果勾选记住我，则将用户名密码保存到本地缓存
								// 原：这里需要保存密码明文，否则登录时又会再加一层密
								// 新：这里保存密码密文，并保存密文md5，用于检测密码是否被重新输入过
								let md5 = hex_md5(_this.user.password);
								LocalStorage.set(LOCAL_KEY_REMEMBER_USER, {
									loginName: loginUser.loginName,
									password: _this.user.password,
									md5: md5
								})
							} else {
								// 没有勾选“记住我”时，要把本地缓存清空，否则按照mounted的逻辑，下次打开时会自动显示用户名密码
								LocalStorage.set(LOCAL_KEY_REMEMBER_USER, null);
							}

							_this.$router.push("/welcome");
						} else {
							Toast.warning(resp.message);
						}
					});
			},


			/**
			 * 加载图形验证码
			 */
			loadImageCode: function() {
				let _this = this;
				_this.imageCodeToken = Tool.uuid(8);
				$('#image-code').attr('src', process.env.VUE_APP_SERVER + '/system/admin/kaptcha/image-code/' + _this.imageCodeToken);
			},
		}
	}
</script>

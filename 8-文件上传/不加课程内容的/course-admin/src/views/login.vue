<template>
	<v-app>
		<v-content>
			<v-container class="fill-height" fluid>
				<v-row align="center" justify="center">
					<v-col col="12" sm="8" md="8">
						<v-card class="elevation-12">
							<v-window v-model="step">
								<v-window-item :value="1">
									<v-row>
										<v-col cols="12" md="8">
											<v-card-text class="mt-12">
												<h1 class="text-center display-2 blue--text text--accent-3">
													登录页面
												</h1>
												<v-form>
													<v-text-field label="Email" name="Email" prepend-icon="email" type="text" color="blue accent-3" />

													<v-text-field id="password" label="Password" name="Password" prepend-icon="lock" type="password" color="blue accent-3" />

												</v-form>

												<h3 class="text-center mt-3">
													忘记密码
												</h3>

											</v-card-text>

											<div class="text-center mt-3">
												<v-btn rounded color="blue accent-3" dark v-on:click="login()">登录</v-btn>
											</div>
										</v-col>

										<v-col cols="12" md="4" class="green accent-3">
											<v-card-text class="blue-grey--text mt-12">
												<h1 class="text-center display-1">你好，朋友!</h1>
												<h5 class="text-center">输入您的个人详细信息，然后开始与我们一起旅行。</h5>
											</v-card-text>
											<div class="text-center">
												<v-btn rounded outlined="" dark v-on:click="change1()">
													注册
												</v-btn>
											</div>
										</v-col>
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
			step: 1,
		}),

		props: {
			source: String
		},

		methods: {
			change1() {
				this.step++;
			},

			change2() {
				this.step--;
			},
			
			login() {
				this.$router.push("/welcome");
			}
		}
	}
</script>

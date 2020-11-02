---
typora-root-url: /images
---



# 使用Vue-Cli搭建管理控台

## 使用vue cli创建admin项目

vue.js vue-cli

* 用法一:直接引入vue.js 适用于现成的jsp项目 thymeleaf项目等,这种用法不算前后端分离,发布时依然是前后端一起部署的
* 用法二使用vue-cli新建项目 前后端分离开发 分离部署



安装nodejs

npm install -g @vue/cli 可以用于初始安装,也可以用于升级到最新版本

vue create admin

vue cli 初始安装带了bable和eslint插件

babel:js编译器, eslint 代码规范检查

选择默认选项就好



main.js是vue入口文件,用来初始化vue实例并集成所需要的插件

把app.vue替换调index.html 里 <div id="app"></div>



## 集成bootstrap后台响应式管理模板

### ace admin  

技术 : ace bootstrap + jquery

http://ace.jeka.by/index.html

admin 增加ace模板

ace放到admin public文件夹下面

所有项目中的静态代码全部放在public目录下

login.html 里 head + 最后的js 全部放到index.html 里的 head

index.html

```html
<!DOCTYPE html>
<html lang="en">
  <!-- <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <link rel="icon" href="<%= BASE_URL %>favicon.ico">
    <title><%= htmlWebpackPlugin.options.title %></title>
  </head> -->

  <head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>Login Page - Ace Admin</title>


		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="<%= BASE_URL %>ace/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="<%= BASE_URL %>ace/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="<%= BASE_URL %>ace/assets/css/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="<%= BASE_URL %>ace/assets/css/ace.min.css" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" />
		<![endif]-->
    <link rel="stylesheet" href="<%= BASE_URL %>ace/assets/css/ace-rtl.min.css" />
    
    <!--[if !IE]> -->
    <script src="<%= BASE_URL %>ace/assets/js/jquery-2.1.4.min.js"></script>

    <!-- <![endif]-->

    <!--[if IE]>
    <script src="assets/js/jquery-1.11.3.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
      if('ontouchstart' in document.documentElement) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
    </script>

  </head>
  


  <body>
    <noscript>
      <strong>We're sorry but <%= htmlWebpackPlugin.options.title %> doesn't work properly without JavaScript enabled. Please enable it to continue.</strong>
    </noscript>
    <div id="app"></div>
    <!-- built files will be auto injected -->
  </body>
</html>

```



关闭代码检查

.eslintrc.js 增加这个文件 用于检测代码规范 ,是一把双刃剑,有一些检测没必要,可以通过修改配置禁用掉

```typescript
module.exports = {
    root: true,
    env: {
        node: true
    },
    'extends': [
        'plugin:vue/essential',
        'eslint:recommended'
    ],
    rules: {
        'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
        'no-undef': 'off',
        'vue/no-unused-vars': 'off',
        'vue/require-v-for-key': 'off',
        'no-unused-vars': 'off',
        'vue/no-unused-components': 'off'
    },
    parserOptions: {
        parser: 'babel-eslint'
    }
};

```



app.vue

```vue
<template>
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>
									<span class="red">Ace</span>
									<span class="white" id="id-text2">Application</span>
								</h1>
								<h4 class="blue" id="id-company-text">&copy; Company Name</h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												Please Enter Your Information
											</h4>

											<div class="space-6"></div>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl"> Remember Me</span>
														</label>

														<button type="button" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">Login</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

											<div class="social-or-login center">
												<span class="bigger-110">Or Login Using</span>
											</div>

											<div class="space-6"></div>

											<div class="social-login center">
												<a class="btn btn-primary">
													<i class="ace-icon fa fa-facebook"></i>
												</a>

												<a class="btn btn-info">
													<i class="ace-icon fa fa-twitter"></i>
												</a>

												<a class="btn btn-danger">
													<i class="ace-icon fa fa-google-plus"></i>
												</a>
											</div>
										</div><!-- /.widget-main -->

										<div class="toolbar clearfix">
											<div>
												<a href="#" data-target="#forgot-box" class="forgot-password-link">
													<i class="ace-icon fa fa-arrow-left"></i>
													I forgot my password
												</a>
											</div>

											<div>
												<a href="#" data-target="#signup-box" class="user-signup-link">
													I want to register
													<i class="ace-icon fa fa-arrow-right"></i>
												</a>
											</div>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="ace-icon fa fa-key"></i>
												Retrieve Password
											</h4>

											<div class="space-6"></div>
											<p>
												Enter your email and to receive instructions
											</p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<div class="clearfix">
														<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="ace-icon fa fa-lightbulb-o"></i>
															<span class="bigger-110">Send Me!</span>
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												Back to login
												<i class="ace-icon fa fa-arrow-right"></i>
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.forgot-box -->

								<div id="signup-box" class="signup-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="ace-icon fa fa-users blue"></i>
												New User Registration
											</h4>

											<div class="space-6"></div>
											<p> Enter your details to begin: </p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Repeat password" />
															<i class="ace-icon fa fa-retweet"></i>
														</span>
													</label>

													<label class="block">
														<input type="checkbox" class="ace" />
														<span class="lbl">
															I accept the
															<a href="#">User Agreement</a>
														</span>
													</label>

													<div class="space-24"></div>

													<div class="clearfix">
														<button type="reset" class="width-30 pull-left btn btn-sm">
															<i class="ace-icon fa fa-refresh"></i>
															<span class="bigger-110">Reset</span>
														</button>

														<button type="button" class="width-65 pull-right btn btn-sm btn-success">
															<span class="bigger-110">Register</span>

															<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
														</button>
													</div>
												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												<i class="ace-icon fa fa-arrow-left"></i>
												Back to login
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.signup-box -->
							</div><!-- /.position-relative -->

							<div class="navbar-fixed-top align-right">
								<br />
								&nbsp;
								<a id="btn-login-dark" href="#">Dark</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-blur" href="#">Blur</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-light" href="#">Light</a>
								&nbsp; &nbsp; &nbsp;
							</div>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

</template>

<script>
$('body').attr('class', 'login-layout light-login');
export default {
  name: 'App',

}
</script>

```





一般后台管理系统是不允许注册,需要由管理员来新建用户

对app.vue在进行修改

```vue
<template>
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>

                  <span class="">控台管理</span>
								</h1>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
											请输入用户名密码
											</h4>

											<div class="space-6"></div>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl"> 记住我</span>
														</label>

														<button type="button" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登录</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>


											<div class="space-6"></div>
										</div><!-- /.widget-main -->

			
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

						
							</div><!-- /.position-relative -->

							<!-- <div class="navbar-fixed-top align-right">
								<br />
								&nbsp;
								<a id="btn-login-dark" href="#">Dark</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-blur" href="#">Blur</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-light" href="#">Light</a>
								&nbsp; &nbsp; &nbsp;
							</div> -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

</template>

<script>
$('body').attr('class', 'login-layout light-login');
export default {
  name: 'App',

}
</script>

```



## 集成路由vue-router

cd admin

cnpm install --save vue-router



app.vue

```
<template>
  <div id="app">
    <router-view/>
  </div>
</template>>
```



router-view是路由嵌套的标签,一般配合父子路由使用



src/view/login.vue  就是原来的app.vue

```vue
<template>
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>

                  <span class="">控台管理</span>
								</h1>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
											请输入用户名密码
											</h4>

											<div class="space-6"></div>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl"> 记住我</span>
														</label>

														<button type="button" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登录</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>


											<div class="space-6"></div>
										</div><!-- /.widget-main -->

			
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

						
							</div><!-- /.position-relative -->

							<!-- <div class="navbar-fixed-top align-right">
								<br />
								&nbsp;
								<a id="btn-login-dark" href="#">Dark</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-blur" href="#">Blur</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-light" href="#">Light</a>
								&nbsp; &nbsp; &nbsp;
							</div> -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

</template>

<script>
$('body').attr('class', 'login-layout light-login');
export default {
  name: 'login',

}
</script>

```



main.js

```vue
import Vue from 'vue'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

```



main.js同目录 router.js

```vue

import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'

Vue.use(Router);

export default new Router(
    {
        mode: 'history',
        base: process.env.BABEL_URL,
        routes: [{
            path: '*',
            redirect: "/login",
        }, {
            path: "/login",
            component: Login
        }]
    }

)
```

angular也有两种url展示形式 hash 或者 history 一般选择 history 美观

### 制作admin页面

template下面只能有一个标签

black.html拷贝过来 body 两个部分 注意 要把script 要去掉

但是样子不太对,这是因为之前在login 给body加上了两个样式

但是这样写,login样式又出错了

后面解决

admin.vue

```vue
<template>
    <div>
        <div id="navbar" class="navbar navbar-default ace-save-state">
            <div class="navbar-container ace-save-state" id="navbar-container">
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler"
                    data-target="#sidebar">
                    <span class="sr-only">Toggle sidebar</span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>
                </button>

                <div class="navbar-header pull-left">
                    <a href="index.html" class="navbar-brand">
                        <small>
                            <i class="fa fa-leaf"></i>
                            Ace Admin
                        </small>
                    </a>
                </div>

                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <ul class="nav ace-nav">
                        <li class="grey dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-tasks"></i>
                                <span class="badge badge-grey">4</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-check"></i>
                                    4 Tasks to complete
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar">
                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Software Update</span>
                                                    <span class="pull-right">65%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:65%" class="progress-bar"></div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Hardware Upgrade</span>
                                                    <span class="pull-right">35%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:35%" class="progress-bar progress-bar-danger">
                                                    </div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Unit Testing</span>
                                                    <span class="pull-right">15%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:15%" class="progress-bar progress-bar-warning">
                                                    </div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Bug Fixes</span>
                                                    <span class="pull-right">90%</span>
                                                </div>

                                                <div class="progress progress-mini progress-striped active">
                                                    <div style="width:90%" class="progress-bar progress-bar-success">
                                                    </div>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        See tasks with details
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="purple dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-bell icon-animated-bell"></i>
                                <span class="badge badge-important">8</span>
                            </a>

                            <ul
                                class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-exclamation-triangle"></i>
                                    8 Notifications
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar navbar-pink">
                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">
                                                        <i class="btn btn-xs no-hover btn-pink fa fa-comment"></i>
                                                        New Comments
                                                    </span>
                                                    <span class="pull-right badge badge-info">+12</span>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <i class="btn btn-xs btn-primary fa fa-user"></i>
                                                Bob just signed up as an editor ...
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">
                                                        <i
                                                            class="btn btn-xs no-hover btn-success fa fa-shopping-cart"></i>
                                                        New Orders
                                                    </span>
                                                    <span class="pull-right badge badge-success">+8</span>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">
                                                        <i class="btn btn-xs no-hover btn-info fa fa-twitter"></i>
                                                        Followers
                                                    </span>
                                                    <span class="pull-right badge badge-info">+11</span>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        See all notifications
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="green dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
                                <span class="badge badge-success">5</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-envelope-o"></i>
                                    5 Messages
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar">
                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="assets/images/avatars/avatar.png" class="msg-photo"
                                                    alt="Alex's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Alex:</span>
                                                        Ciao sociis natoque penatibus et auctor ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>a moment ago</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="assets/images/avatars/avatar3.png" class="msg-photo"
                                                    alt="Susan's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Susan:</span>
                                                        Vestibulum id ligula porta felis euismod ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>20 minutes ago</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="assets/images/avatars/avatar4.png" class="msg-photo"
                                                    alt="Bob's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Bob:</span>
                                                        Nullam quis risus eget urna mollis ornare ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>3:15 pm</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="assets/images/avatars/avatar2.png" class="msg-photo"
                                                    alt="Kate's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Kate:</span>
                                                        Ciao sociis natoque eget urna mollis ornare ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>1:33 pm</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="assets/images/avatars/avatar5.png" class="msg-photo"
                                                    alt="Fred's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Fred:</span>
                                                        Vestibulum id penatibus et auctor ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>10:09 am</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="inbox.html">
                                        See all messages
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="light-blue dropdown-modal">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="assets/images/avatars/user.jpg" alt="Jason's Photo" />
                                <span class="user-info">
                                    <small>Welcome,</small>
                                    Jason
                                </span>

                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul
                                class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-cog"></i>
                                        Settings
                                    </a>
                                </li>

                                <li>
                                    <a href="profile.html">
                                        <i class="ace-icon fa fa-user"></i>
                                        Profile
                                    </a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        Logout
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- /.navbar-container -->
        </div>

        <div class="main-container ace-save-state" id="main-container">
            <!-- <script type="text/javascript">
                try {
                    ace.settings.loadState("main-container");
                } catch (e) { }
            </script> -->

            <div id="sidebar" class="sidebar responsive ace-save-state">
                <!-- <script type="text/javascript">
                    try {
                        ace.settings.loadState("sidebar");
                    } catch (e) { }
                </script> -->

                <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                        <button class="btn btn-success">
                            <i class="ace-icon fa fa-signal"></i>
                        </button>

                        <button class="btn btn-info">
                            <i class="ace-icon fa fa-pencil"></i>
                        </button>

                        <button class="btn btn-warning">
                            <i class="ace-icon fa fa-users"></i>
                        </button>

                        <button class="btn btn-danger">
                            <i class="ace-icon fa fa-cogs"></i>
                        </button>
                    </div>

                    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                        <span class="btn btn-success"></span>

                        <span class="btn btn-info"></span>

                        <span class="btn btn-warning"></span>

                        <span class="btn btn-danger"></span>
                    </div>
                </div>
                <!-- /.sidebar-shortcuts -->

                <ul class="nav nav-list">
                    <li class>
                        <a href="index.html">
                            <i class="menu-icon fa fa-tachometer"></i>
                            <span class="menu-text">Dashboard</span>
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class>
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-desktop"></i>
                            <span class="menu-text">UI &amp; Elements</span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="#" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Layouts
                                    <b class="arrow fa fa-angle-down"></b>
                                </a>

                                <b class="arrow"></b>

                                <ul class="submenu">
                                    <li class>
                                        <a href="top-menu.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Top Menu
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="two-menu-1.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Two Menus 1
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="two-menu-2.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Two Menus 2
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="mobile-menu-1.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Default Mobile Menu
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="mobile-menu-2.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Mobile Menu 2
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="mobile-menu-3.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Mobile Menu 3
                                        </a>

                                        <b class="arrow"></b>
                                    </li>
                                </ul>
                            </li>

                            <li class>
                                <a href="typography.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Typography
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="elements.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Elements
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="buttons.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Buttons &amp; Icons
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="content-slider.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Content Sliders
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="treeview.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Treeview
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="jquery-ui.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    jQuery UI
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="nestable-list.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Nestable Lists
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="#" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Three Level Menu
                                    <b class="arrow fa fa-angle-down"></b>
                                </a>

                                <b class="arrow"></b>

                                <ul class="submenu">
                                    <li class>
                                        <a href="#">
                                            <i class="menu-icon fa fa-leaf green"></i>
                                            Item #1
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="#" class="dropdown-toggle">
                                            <i class="menu-icon fa fa-pencil orange"></i>
                                            4th level
                                            <b class="arrow fa fa-angle-down"></b>
                                        </a>

                                        <b class="arrow"></b>

                                        <ul class="submenu">
                                            <li class>
                                                <a href="#">
                                                    <i class="menu-icon fa fa-plus purple"></i>
                                                    Add Product
                                                </a>

                                                <b class="arrow"></b>
                                            </li>

                                            <li class>
                                                <a href="#">
                                                    <i class="menu-icon fa fa-eye pink"></i>
                                                    View Products
                                                </a>

                                                <b class="arrow"></b>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>

                    <li class>
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text">Tables</span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="tables.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Simple &amp; Dynamic
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="jqgrid.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    jqGrid plugin
                                </a>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>

                    <li class>
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-pencil-square-o"></i>
                            <span class="menu-text">Forms</span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="form-elements.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Form Elements
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="form-elements-2.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Form Elements 2
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="form-wizard.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Wizard &amp; Validation
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="wysiwyg.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Wysiwyg &amp; Markdown
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="dropzone.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Dropzone File Upload
                                </a>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>

                    <li class>
                        <a href="widgets.html">
                            <i class="menu-icon fa fa-list-alt"></i>
                            <span class="menu-text">Widgets</span>
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class>
                        <a href="calendar.html">
                            <i class="menu-icon fa fa-calendar"></i>

                            <span class="menu-text">
                                Calendar
                                <span class="badge badge-transparent tooltip-error" title="2 Important Events">
                                    <i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i>
                                </span>
                            </span>
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class>
                        <a href="gallery.html">
                            <i class="menu-icon fa fa-picture-o"></i>
                            <span class="menu-text">Gallery</span>
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class>
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-tag"></i>
                            <span class="menu-text">More Pages</span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="profile.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    User Profile
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="inbox.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Inbox
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="pricing.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Pricing Tables
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="invoice.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Invoice
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="timeline.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Timeline
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="search.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Search Results
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="email.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Email Templates
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="login.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Login &amp; Register
                                </a>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>

                    <li class="active open">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-file-o"></i>

                            <span class="menu-text">
                                Other Pages
                                <span class="badge badge-primary">5</span>
                            </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="faq.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    FAQ
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="error-404.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Error 404
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="error-500.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Error 500
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="grid.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Grid
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class="active">
                                <a href="blank.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Blank Page
                                </a>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>
                </ul>
                <!-- /.nav-list -->

                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"
                        data-icon1="ace-icon fa fa-angle-double-left"
                        data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>
            </div>

            <div class="main-content">
                <div class="main-content-inner">
                    <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                        <ul class="breadcrumb">
                            <li>
                                <i class="ace-icon fa fa-home home-icon"></i>
                                <a href="#">Home</a>
                            </li>

                            <li>
                                <a href="#">Other Pages</a>
                            </li>
                            <li class="active">Blank Page</li>
                        </ul>
                        <!-- /.breadcrumb -->

                        <div class="nav-search" id="nav-search">
                            <form class="form-search">
                                <span class="input-icon">
                                    <input type="text" placeholder="Search ..." class="nav-search-input"
                                        id="nav-search-input" autocomplete="off" />
                                    <i class="ace-icon fa fa-search nav-search-icon"></i>
                                </span>
                            </form>
                        </div>
                        <!-- /.nav-search -->
                    </div>

                    <div class="page-content">
                        <div class="ace-settings-container" id="ace-settings-container">
                            <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                                <i class="ace-icon fa fa-cog bigger-130"></i>
                            </div>

                            <div class="ace-settings-box clearfix" id="ace-settings-box">
                                <div class="pull-left width-50">
                                    <div class="ace-settings-item">
                                        <div class="pull-left">
                                            <select id="skin-colorpicker" class="hide">
                                                <option data-skin="no-skin" value="#438EB9">#438EB9</option>
                                                <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                                                <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                                                <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                                            </select>
                                        </div>
                                        <span>&nbsp; Choose Skin</span>
                                    </div>

                                    <div class="ace-settings-item">
                                        <input type="checkbox" class="ace ace-checkbox-2 ace-save-state"
                                            id="ace-settings-navbar" autocomplete="off" />
                                        <label class="lbl" for="ace-settings-navbar">Fixed Navbar</label>
                                    </div>

                                    <div class="ace-settings-item">
                                        <input type="checkbox" class="ace ace-checkbox-2 ace-save-state"
                                            id="ace-settings-sidebar" autocomplete="off" />
                                        <label class="lbl" for="ace-settings-sidebar">Fixed Sidebar</label>
                                    </div>

                                    <div class="ace-settings-item">
                                        <input type="checkbox" class="ace ace-checkbox-2 ace-save-state"
                                            id="ace-settings-breadcrumbs" autocomplete="off" />
                                        <label class="lbl" for="ace-settings-breadcrumbs">Fixed Breadcrumbs</label>
                                    </div>

                                    <div class="ace-settings-item">
                                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl"
                                            autocomplete="off" />
                                        <label class="lbl" for="ace-settings-rtl">Right To Left (rtl)</label>
                                    </div>

                                    <div class="ace-settings-item">
                                        <input type="checkbox" class="ace ace-checkbox-2 ace-save-state"
                                            id="ace-settings-add-container" autocomplete="off" />
                                        <label class="lbl" for="ace-settings-add-container">
                                            Inside
                                            <b>.container</b>
                                        </label>
                                    </div>
                                </div>
                                <!-- /.pull-left -->

                                <div class="pull-left width-50">
                                    <div class="ace-settings-item">
                                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover"
                                            autocomplete="off" />
                                        <label class="lbl" for="ace-settings-hover">Submenu on Hover</label>
                                    </div>

                                    <div class="ace-settings-item">
                                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact"
                                            autocomplete="off" />
                                        <label class="lbl" for="ace-settings-compact">Compact Sidebar</label>
                                    </div>

                                    <div class="ace-settings-item">
                                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight"
                                            autocomplete="off" />
                                        <label class="lbl" for="ace-settings-highlight">Alt. Active Item</label>
                                    </div>
                                </div>
                                <!-- /.pull-left -->
                            </div>
                            <!-- /.ace-settings-box -->
                        </div>
                        <!-- /.ace-settings-container -->

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->

                                <!-- PAGE CONTENT ENDS -->
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- /.page-content -->
                </div>
            </div>
            <!-- /.main-content -->

            <div class="footer">
                <div class="footer-inner">
                    <div class="footer-content">
                        <span class="bigger-120">
                            <span class="blue bolder">Ace</span>
                            Application &copy; 2013-2014
                        </span>
                        &nbsp; &nbsp;
                        <span class="action-buttons">
                            <a href="#">
                                <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                            </a>

                            <a href="#">
                                <i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
                            </a>

                            <a href="#">
                                <i class="ace-icon fa fa-rss-square orange bigger-150"></i>
                            </a>
                        </span>
                    </div>
                </div>
            </div>

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div>
        <!-- /.main-container -->
    </div>
</template>

<script>
    $('body').removeAttr('class', 'login-layout light-login');
    $('body').attr('class', 'no-skin');
export default {
  name: 'admin',

}
</script>
```



### login页面和admin页面的跳转

```vue
<template>
  <div class="main-container">
    <div class="main-content">
      <div class="row">
        <div class="col-sm-10 col-sm-offset-1">
          <div class="login-container">
            <div class="center">
              <h1>
                <i class="ace-icon fa fa-leaf green"></i>

                <span class>控台管理</span>
              </h1>
            </div>

            <div class="space-6"></div>

            <div class="position-relative">
              <div id="login-box" class="login-box visible widget-box no-border">
                <div class="widget-body">
                  <div class="widget-main">
                    <h4 class="header blue lighter bigger">
                      <i class="ace-icon fa fa-coffee green"></i>
                      请输入用户名密码
                    </h4>

                    <div class="space-6"></div>

                    <form>
                      <fieldset>
                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <input type="text" class="form-control" placeholder="用户名" />
                            <i class="ace-icon fa fa-user"></i>
                          </span>
                        </label>

                        <label class="block clearfix">
                          <span class="block input-icon input-icon-right">
                            <input type="password" class="form-control" placeholder="密码" />
                            <i class="ace-icon fa fa-lock"></i>
                          </span>
                        </label>

                        <div class="space"></div>

                        <div class="clearfix">
                          <label class="inline">
                            <input type="checkbox" class="ace" />
                            <span class="lbl">记住我</span>
                          </label>

                          <button type="button" class="width-35 pull-right btn btn-sm btn-primary"
                          @click="login()">
                            <i class="ace-icon fa fa-key"></i>
                            <span class="bigger-110">登录</span>
                          </button>
                        </div>

                        <div class="space-4"></div>
                      </fieldset>
                    </form>

                    <div class="space-6"></div>
                  </div>
                  <!-- /.widget-main -->
                </div>
                <!-- /.widget-body -->
              </div>
              <!-- /.login-box -->
            </div>
            <!-- /.position-relative -->
          </div>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </div>
    <!-- /.main-content -->
  </div>
  <!-- /.main-container -->
</template>

<script>
$("body").attr("class", "login-layout light-login");
export default {
  name: "login",
  methods: {
      login() {
          this.$router.push("/admin");
      }
  }
};
</script>

```





## 控台欢迎页面开发

### mouted的使用

组件初始化

上一节login和admin的样式问题

admin

```typescript
export default {
  name: 'admin',
  mounted: function() {
    $('body').removeAttr('class', 'login-layout light-login');
    $('body').attr('class', 'no-skin');
  }


}

```

login

```typescript
  name: "login",
  mounted: function() {
    $("body").attr("class", "login-layout light-login");
  },
  methods: {
    login() {
      this.$router.push("/admin");
    }
  }
};


```



mounted 组件每次加载,mounted都会再次执行

### 使用router-view增加welcome子组件

制作欢迎页面

src/views/admin/welcome.vue

admin下面的子组件全放在这里

```
<template>
    <div>
        <h1>您好，欢迎进入控制台</h1>
    </div>
</template>>
```



router 修改

```
import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import Admin from './views/admin.vue'
import Welcome from './views/admin/welcome.vue'

Vue.use(Router);

export default new Router(
    {
        mode: 'history',
        base: process.env.BASE_URL,
        routes: [{
            path: '*',
            redirect: "/login",
        }, {
            path: "/login",
            component: Login
        }, {
            path: "/admin",
            component: Admin,
            children: [{
            // 子路由不能加斜杠
                path: 'welcome',
                component: Welcome

            }]
        }]
    }

)
```



修改admin.vue

```
<div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <router-view></router-view>
                                <!-- PAGE CONTENT ENDS -->
                            </div>
                            <!-- /.col -->
                        </div>
```

子路由的配置不要/开头

### 控台页面元素修改

将面包屑导航 搜索 设置删除

```vue
<template>
    <div>
        <div id="navbar" class="navbar navbar-default ace-save-state">
            <div class="navbar-container ace-save-state" id="navbar-container">
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler"
                    data-target="#sidebar">
                    <span class="sr-only">Toggle sidebar</span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>
                </button>

                <div class="navbar-header pull-left">
                    <a href="index.html" class="navbar-brand">
                        <small>
                            <i class="fa fa-leaf"></i>
                            Ace Admin
                        </small>
                    </a>
                </div>

                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <ul class="nav ace-nav">
                        <li class="grey dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-tasks"></i>
                                <span class="badge badge-grey">4</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-check"></i>
                                    4 Tasks to complete
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar">
                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Software Update</span>
                                                    <span class="pull-right">65%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:65%" class="progress-bar"></div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Hardware Upgrade</span>
                                                    <span class="pull-right">35%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:35%" class="progress-bar progress-bar-danger">
                                                    </div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Unit Testing</span>
                                                    <span class="pull-right">15%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:15%" class="progress-bar progress-bar-warning">
                                                    </div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Bug Fixes</span>
                                                    <span class="pull-right">90%</span>
                                                </div>

                                                <div class="progress progress-mini progress-striped active">
                                                    <div style="width:90%" class="progress-bar progress-bar-success">
                                                    </div>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        See tasks with details
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="purple dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-bell icon-animated-bell"></i>
                                <span class="badge badge-important">8</span>
                            </a>

                            <ul
                                class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-exclamation-triangle"></i>
                                    8 Notifications
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar navbar-pink">
                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">
                                                        <i class="btn btn-xs no-hover btn-pink fa fa-comment"></i>
                                                        New Comments
                                                    </span>
                                                    <span class="pull-right badge badge-info">+12</span>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <i class="btn btn-xs btn-primary fa fa-user"></i>
                                                Bob just signed up as an editor ...
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">
                                                        <i
                                                            class="btn btn-xs no-hover btn-success fa fa-shopping-cart"></i>
                                                        New Orders
                                                    </span>
                                                    <span class="pull-right badge badge-success">+8</span>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">
                                                        <i class="btn btn-xs no-hover btn-info fa fa-twitter"></i>
                                                        Followers
                                                    </span>
                                                    <span class="pull-right badge badge-info">+11</span>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        See all notifications
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="green dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
                                <span class="badge badge-success">5</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-envelope-o"></i>
                                    5 Messages
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar">
                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="assets/images/avatars/avatar.png" class="msg-photo"
                                                    alt="Alex's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Alex:</span>
                                                        Ciao sociis natoque penatibus et auctor ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>a moment ago</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="assets/images/avatars/avatar3.png" class="msg-photo"
                                                    alt="Susan's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Susan:</span>
                                                        Vestibulum id ligula porta felis euismod ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>20 minutes ago</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="assets/images/avatars/avatar4.png" class="msg-photo"
                                                    alt="Bob's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Bob:</span>
                                                        Nullam quis risus eget urna mollis ornare ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>3:15 pm</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="assets/images/avatars/avatar2.png" class="msg-photo"
                                                    alt="Kate's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Kate:</span>
                                                        Ciao sociis natoque eget urna mollis ornare ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>1:33 pm</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="assets/images/avatars/avatar5.png" class="msg-photo"
                                                    alt="Fred's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Fred:</span>
                                                        Vestibulum id penatibus et auctor ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>10:09 am</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="inbox.html">
                                        See all messages
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="light-blue dropdown-modal">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="assets/images/avatars/user.jpg" alt="Jason's Photo" />
                                <span class="user-info">
                                    <small>Welcome,</small>
                                    Jason
                                </span>

                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul
                                class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-cog"></i>
                                        Settings
                                    </a>
                                </li>

                                <li>
                                    <a href="profile.html">
                                        <i class="ace-icon fa fa-user"></i>
                                        Profile
                                    </a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        Logout
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- /.navbar-container -->
        </div>

        <div class="main-container ace-save-state" id="main-container">
            <!-- <script type="text/javascript">
                try {
                    ace.settings.loadState("main-container");
                } catch (e) { }
            </script> -->

            <div id="sidebar" class="sidebar responsive ace-save-state">
                <!-- <script type="text/javascript">
                    try {
                        ace.settings.loadState("sidebar");
                    } catch (e) { }
                </script> -->

                <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                        <button class="btn btn-success">
                            <i class="ace-icon fa fa-signal"></i>
                        </button>

                        <button class="btn btn-info">
                            <i class="ace-icon fa fa-pencil"></i>
                        </button>

                        <button class="btn btn-warning">
                            <i class="ace-icon fa fa-users"></i>
                        </button>

                        <button class="btn btn-danger">
                            <i class="ace-icon fa fa-cogs"></i>
                        </button>
                    </div>

                    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                        <span class="btn btn-success"></span>

                        <span class="btn btn-info"></span>

                        <span class="btn btn-warning"></span>

                        <span class="btn btn-danger"></span>
                    </div>
                </div>
                <!-- /.sidebar-shortcuts -->

                <ul class="nav nav-list">
                    <li class>
                        <a href="index.html">
                            <i class="menu-icon fa fa-tachometer"></i>
                            <span class="menu-text">Dashboard</span>
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class>
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-desktop"></i>
                            <span class="menu-text">UI &amp; Elements</span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="#" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Layouts
                                    <b class="arrow fa fa-angle-down"></b>
                                </a>

                                <b class="arrow"></b>

                                <ul class="submenu">
                                    <li class>
                                        <a href="top-menu.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Top Menu
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="two-menu-1.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Two Menus 1
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="two-menu-2.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Two Menus 2
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="mobile-menu-1.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Default Mobile Menu
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="mobile-menu-2.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Mobile Menu 2
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="mobile-menu-3.html">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            Mobile Menu 3
                                        </a>

                                        <b class="arrow"></b>
                                    </li>
                                </ul>
                            </li>

                            <li class>
                                <a href="typography.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Typography
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="elements.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Elements
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="buttons.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Buttons &amp; Icons
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="content-slider.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Content Sliders
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="treeview.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Treeview
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="jquery-ui.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    jQuery UI
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="nestable-list.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Nestable Lists
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="#" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Three Level Menu
                                    <b class="arrow fa fa-angle-down"></b>
                                </a>

                                <b class="arrow"></b>

                                <ul class="submenu">
                                    <li class>
                                        <a href="#">
                                            <i class="menu-icon fa fa-leaf green"></i>
                                            Item #1
                                        </a>

                                        <b class="arrow"></b>
                                    </li>

                                    <li class>
                                        <a href="#" class="dropdown-toggle">
                                            <i class="menu-icon fa fa-pencil orange"></i>
                                            4th level
                                            <b class="arrow fa fa-angle-down"></b>
                                        </a>

                                        <b class="arrow"></b>

                                        <ul class="submenu">
                                            <li class>
                                                <a href="#">
                                                    <i class="menu-icon fa fa-plus purple"></i>
                                                    Add Product
                                                </a>

                                                <b class="arrow"></b>
                                            </li>

                                            <li class>
                                                <a href="#">
                                                    <i class="menu-icon fa fa-eye pink"></i>
                                                    View Products
                                                </a>

                                                <b class="arrow"></b>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>

                    <li class>
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text">Tables</span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="tables.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Simple &amp; Dynamic
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="jqgrid.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    jqGrid plugin
                                </a>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>

                    <li class>
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-pencil-square-o"></i>
                            <span class="menu-text">Forms</span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="form-elements.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Form Elements
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="form-elements-2.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Form Elements 2
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="form-wizard.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Wizard &amp; Validation
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="wysiwyg.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Wysiwyg &amp; Markdown
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="dropzone.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Dropzone File Upload
                                </a>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>

                    <li class>
                        <a href="widgets.html">
                            <i class="menu-icon fa fa-list-alt"></i>
                            <span class="menu-text">Widgets</span>
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class>
                        <a href="calendar.html">
                            <i class="menu-icon fa fa-calendar"></i>

                            <span class="menu-text">
                                Calendar
                                <span class="badge badge-transparent tooltip-error" title="2 Important Events">
                                    <i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i>
                                </span>
                            </span>
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class>
                        <a href="gallery.html">
                            <i class="menu-icon fa fa-picture-o"></i>
                            <span class="menu-text">Gallery</span>
                        </a>

                        <b class="arrow"></b>
                    </li>

                    <li class>
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-tag"></i>
                            <span class="menu-text">More Pages</span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="profile.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    User Profile
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="inbox.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Inbox
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="pricing.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Pricing Tables
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="invoice.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Invoice
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="timeline.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Timeline
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="search.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Search Results
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="email.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Email Templates
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="login.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Login &amp; Register
                                </a>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>

                    <li class="active open">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-file-o"></i>

                            <span class="menu-text">
                                Other Pages
                                <span class="badge badge-primary">5</span>
                            </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="faq.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    FAQ
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="error-404.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Error 404
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="error-500.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Error 500
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="grid.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Grid
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class="active">
                                <a href="blank.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Blank Page
                                </a>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>
                </ul>
                <!-- /.nav-list -->

                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"
                        data-icon1="ace-icon fa fa-angle-double-left"
                        data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>
            </div>

            <div class="main-content">
                <div class="main-content-inner">
   

                    <div class="page-content">
          
                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <router-view></router-view>
                                <!-- PAGE CONTENT ENDS -->
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- /.page-content -->
                </div>
            </div>
            <!-- /.main-content -->

            <div class="footer">
                <div class="footer-inner">
                    <div class="footer-content">
                        <span class="bigger-120">
                            <span class="blue bolder">Ace</span>
                            Application &copy; 2013-2014
                        </span>
                        &nbsp; &nbsp;
                        <span class="action-buttons">
                            <a href="#">
                                <i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
                            </a>

                            <a href="#">
                                <i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
                            </a>

                            <a href="#">
                                <i class="ace-icon fa fa-rss-square orange bigger-150"></i>
                            </a>
                        </span>
                    </div>
                </div>
            </div>

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div>
        <!-- /.main-container -->
    </div>
</template>

<script>
export default {
  name: 'admin',
  mounted: function() {
    $('body').removeAttr('class', 'login-layout light-login');
    $('body').attr('class', 'no-skin');
  }


}
</script>
```





修改顶部

data-toggle 用于按钮和模态框(弹出层)的事件绑定 需要引入东西



找到 ace/index.html

复制一写js到public/index.html

```
<!DOCTYPE html>
<html lang="en">
  <!-- <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <link rel="icon" href="<%= BASE_URL %>favicon.ico">
    <title><%= htmlWebpackPlugin.options.title %></title>
  </head> -->

  <head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>Login Page - Ace Admin</title>


		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="<%= BASE_URL %>ace/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="<%= BASE_URL %>ace/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="<%= BASE_URL %>ace/assets/css/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="<%= BASE_URL %>ace/assets/css/ace.min.css" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" />
		<![endif]-->
    <link rel="stylesheet" href="<%= BASE_URL %>ace/assets/css/ace-rtl.min.css" />
    
    <!--[if !IE]> -->
    <script src="<%= BASE_URL %>ace/assets/js/jquery-2.1.4.min.js"></script>

    <!-- <![endif]-->

    <!--[if IE]>
    <script src="assets/js/jquery-1.11.3.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
      if('ontouchstart' in document.documentElement) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
    </script>

    <script src="<%= BASE_URL %>ace/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="assets/js/excanvas.min.js"></script>
		<![endif]-->
		<script src="<%= BASE_URL %>ace/assets/js/jquery-ui.custom.min.js"></script>
		<script src="<%= BASE_URL %>ace/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="<%= BASE_URL %>ace/assets/js/jquery.easypiechart.min.js"></script>
		<script src="<%= BASE_URL %>ace/assets/js/jquery.sparkline.index.min.js"></script>
		<script src="<%= BASE_URL %>ace/assets/js/jquery.flot.min.js"></script>
		<script src="<%= BASE_URL %>ace/assets/js/jquery.flot.pie.min.js"></script>
		<script src="<%= BASE_URL %>ace/assets/js/jquery.flot.resize.min.js"></script>

		<!-- ace scripts -->
		<script src="<%= BASE_URL %>ace/assets/js/ace-elements.min.js"></script>
		<script src="<%= BASE_URL %>ace/assets/js/ace.min.js"></script>

  </head>
  


  <body>
    <noscript>
      <strong>We're sorry but <%= htmlWebpackPlugin.options.title %> doesn't work properly without JavaScript enabled. Please enable it to continue.</strong>
    </noscript>
    <div id="app"></div>
    <!-- built files will be auto injected -->
  </body>
</html>

```



解决头像问题

../../public/



active : 当前菜单是激活样式(高亮,粗体,字体颜色是蓝色等, open把当前菜单下的子菜单展开

```
<template>
    <div>
        <div id="navbar" class="navbar navbar-default ace-save-state">
            <div class="navbar-container ace-save-state" id="navbar-container">
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler"
                    data-target="#sidebar">
                    <span class="sr-only">Toggle sidebar</span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>
                </button>

                <div class="navbar-header pull-left">
                    <a href="index.html" class="navbar-brand">
                        <small>
                            <i class="fa fa-leaf"></i>
                            在线视频系统
                        </small>
                    </a>
                </div>

                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <ul class="nav ace-nav">
                        <li class="grey dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-tasks"></i>
                                <span class="badge badge-grey">4</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-check"></i>
                                    4 Tasks to complete
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar">
                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Software Update</span>
                                                    <span class="pull-right">65%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:65%" class="progress-bar"></div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Hardware Upgrade</span>
                                                    <span class="pull-right">35%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:35%" class="progress-bar progress-bar-danger">
                                                    </div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Unit Testing</span>
                                                    <span class="pull-right">15%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:15%" class="progress-bar progress-bar-warning">
                                                    </div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Bug Fixes</span>
                                                    <span class="pull-right">90%</span>
                                                </div>

                                                <div class="progress progress-mini progress-striped active">
                                                    <div style="width:90%" class="progress-bar progress-bar-success">
                                                    </div>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        See tasks with details
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="purple dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-bell icon-animated-bell"></i>
                                <span class="badge badge-important">8</span>
                            </a>

                            <ul
                                class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-exclamation-triangle"></i>
                                    8 Notifications
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar navbar-pink">
                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">
                                                        <i class="btn btn-xs no-hover btn-pink fa fa-comment"></i>
                                                        New Comments
                                                    </span>
                                                    <span class="pull-right badge badge-info">+12</span>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <i class="btn btn-xs btn-primary fa fa-user"></i>
                                                Bob just signed up as an editor ...
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">
                                                        <i
                                                            class="btn btn-xs no-hover btn-success fa fa-shopping-cart"></i>
                                                        New Orders
                                                    </span>
                                                    <span class="pull-right badge badge-success">+8</span>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">
                                                        <i class="btn btn-xs no-hover btn-info fa fa-twitter"></i>
                                                        Followers
                                                    </span>
                                                    <span class="pull-right badge badge-info">+11</span>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        See all notifications
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="green dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
                                <span class="badge badge-success">5</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-envelope-o"></i>
                                    5 Messages
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar">
                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar.png"
                                                    class="msg-photo" alt="Alex's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Alex:</span>
                                                        Ciao sociis natoque penatibus et auctor ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>a moment ago</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar3.png"
                                                    class="msg-photo" alt="Susan's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Susan:</span>
                                                        Vestibulum id ligula porta felis euismod ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>20 minutes ago</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar4.png"
                                                    class="msg-photo" alt="Bob's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Bob:</span>
                                                        Nullam quis risus eget urna mollis ornare ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>3:15 pm</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar2.png"
                                                    class="msg-photo" alt="Kate's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Kate:</span>
                                                        Ciao sociis natoque eget urna mollis ornare ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>1:33 pm</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar5.png"
                                                    class="msg-photo" alt="Fred's Avatar" />
                                                <span class="msg-body">
                                                    <span class="msg-title">
                                                        <span class="blue">Fred:</span>
                                                        Vestibulum id penatibus et auctor ...
                                                    </span>

                                                    <span class="msg-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                        <span>10:09 am</span>
                                                    </span>
                                                </span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="inbox.html">
                                        See all messages
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="light-blue dropdown-modal">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="../../public/ace/assets/images/avatars/user.jpg"
                                    alt="Jason's Photo" />
                                <span class="user-info">
                                    <small>Welcome,</small>
                                    Jason
                                </span>

                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul
                                class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-cog"></i>
                                        Settings
                                    </a>
                                </li>

                                <li>
                                    <a href="profile.html">
                                        <i class="ace-icon fa fa-user"></i>
                                        Profile
                                    </a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        Logout
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- /.navbar-container -->
        </div>

        <div class="main-container ace-save-state" id="main-container">
            <!-- <script type="text/javascript">
                try {
                    ace.settings.loadState("main-container");
                } catch (e) { }
            </script> -->

            <div id="sidebar" class="sidebar responsive ace-save-state">
                <!-- <script type="text/javascript">
                    try {
                        ace.settings.loadState("sidebar");
                    } catch (e) { }
                </script> -->

                <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                        <button class="btn btn-success">
                            <i class="ace-icon fa fa-signal"></i>
                        </button>

                        <button class="btn btn-info">
                            <i class="ace-icon fa fa-pencil"></i>
                        </button>

                        <button class="btn btn-warning">
                            <i class="ace-icon fa fa-users"></i>
                        </button>

                        <button class="btn btn-danger">
                            <i class="ace-icon fa fa-cogs"></i>
                        </button>
                    </div>

                    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                        <span class="btn btn-success"></span>

                        <span class="btn btn-info"></span>

                        <span class="btn btn-warning"></span>

                        <span class="btn btn-danger"></span>
                    </div>
                </div>
                <!-- /.sidebar-shortcuts -->

                <ul class="nav nav-list">
                    <li class>
                        <a href="index.html">
                            <i class="menu-icon fa fa-tachometer"></i>
                            <span class="menu-text">欢迎</span>
                        </a>

                        <b class="arrow"></b>
                    </li>



                    <li class="active open">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text">系统管理</span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li class>
                                <a href="tables.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    用户管理
                                </a>

                                <b class="arrow"></b>
                            </li>

                            <li class>
                                <a href="jqgrid.html">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    权限管理
                                </a>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>


                </ul>
                <!-- /.nav-list -->

                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"
                        data-icon1="ace-icon fa fa-angle-double-left"
                        data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>
            </div>

            <div class="main-content">
                <div class="main-content-inner">


                    <div class="page-content">

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <router-view></router-view>
                                <!-- PAGE CONTENT ENDS -->
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- /.page-content -->
                </div>
            </div>
            <!-- /.main-content -->

            <div class="footer">
                <div class="footer-inner">
                    <div class="footer-content">
                        <span class="bigger-120">
                            <span class="blue bolder">无敌</span>
                            版权所有 &copy; 2020
                        </span>
                        &nbsp; &nbsp;

                    </div>
                </div>
            </div>

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div>
        <!-- /.main-container -->
    </div>
</template>

<script>
    export default {
        name: 'admin',
        mounted: function () {
            $('body').removeAttr('class', 'login-layout light-login');
            $('body').attr('class', 'no-skin');
        }


    }
</script>
```
















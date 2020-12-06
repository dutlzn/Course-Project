import Vue from 'vue'
// import VueRouter from 'vue-router'
import Router from 'vue-router'
// 导入组件
import Login from '../views/login.vue'
import Admin from '../views/admin.vue'
import Welcome from '../views/admin/welcome.vue'
import Category from '../views/admin/category.vue'
import Teacher from '../views/admin/teacher.vue'
import Course from '../views/admin/course.vue'
import Chapter from '../views/admin/chapter.vue'
import Section from '../views/admin/section.vue'
import File from '../views/admin/file.vue'
// Vue.use(VueRouter);
Vue.use(Router);

// const routes = [
	
// ]

// const router = new VueRouter({
//   mode: 'history',
//   base: process.env.BASE_URL,
//   routes
// })

// export default router
export default new Router(
    {
        mode: 'history', // history hash 推荐使用history hash 的话前面由前缀，url形式不美观，history推荐使用，路由前面只有以恶搞#
        base: process.env.BASE_URL,
        routes: [{
            path: '*',
            redirect: "/login",
        }, {
            path: "/login",
            component: Login
        }, {
            path: "/",
            name: "admin", 
            // name属性后面会用到
            component: Admin,
            children: [{
                path: 'welcome',
                name: 'welcome',
                component: Welcome
            }, {
                path: 'business/category',
                name: 'business/category',
                component: Category
            }, {
							path: 'business/teacher',
							name: 'business/teacher',
							component: Teacher
						}, {
							path: 'business/course',
							name: 'business/course',
							component: Course
						}, {
							path: 'business/chapter',
							name: 'business/chapter',
							component: Chapter
						}, {
							path: 'business/section',
							name: 'business/section',
							component: Section
						}, {
							path: 'file/file',
							name: 'file/file',
							component: File
						}]
        }],
				
				ScrollBehavior(to ,from, savedPosition) {
					return {x: 0, y:0};
				}
    }

)
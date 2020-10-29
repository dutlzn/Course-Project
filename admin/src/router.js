import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import Admin from './views/admin.vue'
import Welcome from './views/admin/welcome.vue'
import Chapter from './views/admin/chapter.vue'

Vue.use(Router);

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
            component: Admin,
            children: [{
                path: 'welcome',
                name: 'welcome',
                component: Welcome

            }, {
                path: 'business/chapter',
                name: 'business/chapter',
                component: Chapter
            }]
        }]
    }

)
import { Toast } from "vant";
import { createRouter,createWebHashHistory } from "vue-router"; 
const router=createRouter({
    history:createWebHashHistory(),
    routes:[
        // 重定向到home
        {
            path:'/',
            redirect:'home' 
        },
        {
            path:"/home",
            component:()=>import("../views/home")
        },
        {
            path:"/order",
            component:()=>import("../views/order"),
            meta:{
                //当true说明要经过路由守卫
                isAuth:true
            }
        },
        {
            path:"/mine",
            component:()=>import("../views/mine"),
            meta:{
                //当true说明要经过路由守卫
                isAuth:true
            }
        },
        {
            path:"/cart",
            component:()=>import("../views/cart"),
            meta:{
                //当true说明要经过路由守卫
                isAuth:true
            }
        },
        {
            path:"/store",
            component:()=>import("../views/store")
        },
        {
            path:"/createorder",
            component:()=>import("../views/cart/createOrder"),
            meta:{
                //当true说明要经过路由守卫
                isAuth:true
            }
        },
        {
            path:"/address",
            component:()=>import("../views/address"),
            meta:{
                //当true说明要经过路由守卫
                isAuth:true
            }
        },
        {
            path:"/addressedit",
            component:()=>import('../views/addressEdit'),
            meta:{
                //当true说明要经过路由守卫
                isAuth:true
            }
        },
        {
            path:"/userinfoedit",
            component:()=>import('../views/userinfoedit'),
            meta:{
                //当true说明要经过路由守卫
                isAuth:true
            }
        },
        {
            path:'/login',
            component:()=>import('../views/login')
        },
        {
            path:'/register',
            component:()=>import('../views/register')
        }
    ]
})
router.beforeEach((to,from,next)=>{
    // 若to页面需要看你是否登录，则检查登录情况
    if(to.meta.isAuth){
        if(localStorage.isLogin==='login'){
            next();
        }
        else{
            next('/login')
            Toast('请先登录！')
        }
    }
    // 否则直接跳转
    else{
        next()
    }
})
export default router;
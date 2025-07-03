// 导入组件
import Vue from "vue";
import Router from "vue-router";
// 登录
import login from "@/views/login";
import showHtml from "@/views/showHtml";

// 首页
import adminIndex from "@/views/admin/index";


// 启用路由
Vue.use(Router);

// 导出路由
export default new Router({
  routes: [
    {
      path: "/",
      name: "",
      redirect: "/login"
    },
    {
      path: "/login",
      name: "Login",
      component: login
        },
    {
      path: "/showHtml",
      name: "showHTML",
      component: showHtml
    },
    {
      path: "/admin",
      name: "Index",
      component: adminIndex,
      redirect: "/admin/home",
      children: [
        {
          path: "home",
          name: "HOME",
          icon: "el-icon-phone",
          component: () => import("@/views/admin/home"),
        },
        {
          path: "user",
          name: "USER MANAGEMENT",
          icon: "el-icon-user",
          component: () => import("@/views/admin/user.vue"),
        },
        {
          path: "slide",
          name: "SLIDE MANAGEMENT",
          icon: "el-icon-picture",
          component: () => import("@/views/admin/slide.vue"),
        },
        {
          path: "category",
          name: "FOOD CATEGORY",
          icon: "el-icon-s-flag",
          component: () => import("@/views/admin/category.vue"),
        },
        {
          path: "food",
          name: "FOOD DETAIL",
          icon: "el-icon-s-flag",
          component: () => import("@/views/admin/food.vue"),
        },{
          path: "orders",
          name: "ORDERS",
          icon: "el-icon-s-flag",
          component: () => import("@/views/admin/orders.vue"),
        },
        {
          path: "consulation",
          name: "CONSULATION",
          icon: "el-icon-s-promotion",
          component: () => import("@/views/admin/consulation.vue"),
        },{
          path: "config",
          name: "CONFIG PAYMENT QR",
          icon: "el-icon-s-promotion",
          component: () => import("@/views/admin/config.vue"),
        },
      ]
    }
  ]
});

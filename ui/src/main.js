import Vue from "vue";
import App from "./App";

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import locale from 'element-ui/lib/locale/lang/en'

Vue.use(ElementUI, {locale});


import router from "./router";
// 路由拦截器
router.beforeEach((to, from, next) => {

  if (to.path != "/login" && to.path != "/register") {
    let token = localStorage.getItem("token");
    if (token) {
      next();
    } else {
      next("/login");
    }
  } else {
    next();
  }
});

import axios from "axios";
axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf8";
axios.defaults.headers.put["Content-Type"] = "application/json;charset=utf8";
axios.defaults.headers["Authorization"] =
  localStorage.getItem("token") ? "Bearer " + localStorage.getItem("token") : "";

axios.interceptors.response.use(response => {
  return response.data;
});
Vue.prototype.$axios = axios;

import status from './utils/status'

Vue.prototype.$status = status;

import "./assets/icon/iconfont.css";

import echarts from "echarts";
import china from "echarts/map/json/china.json";
import chinaCities from "echarts/map/json/china-cities.json";
echarts.registerMap("china", china);
echarts.registerMap("china-cities", chinaCities);
Vue.prototype.$echarts = echarts;

import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css' // import styles
import 'quill/dist/quill.snow.css' // for snow theme
import 'quill/dist/quill.bubble.css' // for bubble theme
Vue.use(VueQuillEditor, /* { default global options } */)

Vue.config.productionTip = true;

import validationUtils from "./utils/validationUtils";
import {util} from "echarts/lib/export";
Vue.prototype.$validationUtils = validationUtils;
Vue.prototype.$api = "http://localhost:8080/";


Vue.prototype.$seeQuillEditor = (content) => {
  localStorage.setItem("htmlContent", content);
  const url = router.resolve({path: "/showHtml"})
  window.open(url.href, "_blank");
}

new Vue({
  el: "#app",
  router,
  components: {App},
  template: "<App/>"
});

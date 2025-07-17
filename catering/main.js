import App from './App'

import Vue from 'vue'
Vue.prototype.$api = "http://192.168.100.103:8080/"
// 192.168.100.103
	// 此处切换IPV4 地址
import uView from "@/uni_modules/uview-ui"
Vue.use(uView)
import './uni.promisify.adaptor'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
	...App
})
// 引入请求封装，将app参数传递到配置中
require('@/config/request.js')(app)

app.$mount()
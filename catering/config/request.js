// 此vm参数为页面的实例，可以通过它引用vuex中的变量
module.exports = (vm) => {
	// 初始化请求配置
	uni.$u.http.setConfig((config) => {
		/* config 为默认全局配置*/
		config.baseURL = 'http://192.168.100.103:8080/'; /* 根域名 此处切换IPV4 地址*/
		//  192.168.100.103

		return config
	})

	// 请求拦截
	uni.$u.http.interceptors.request.use((config) => { // 可使用async await 做异步操作
		// 带上token
		config.header.Authorization = uni.getStorageSync("token")
		return config
	}, config => { // 可使用async await 做异步操作
		return Promise.reject(config)
	})

	// 响应拦截
	uni.$u.http.interceptors.response.use((response) => {
		const data = response.data
		return data
	}, (response) => {
		return Promise.reject(response)
	})
}
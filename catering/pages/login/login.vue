<template>
	<view style="width: 80%;margin: 100rpx auto;">
		<u-toast ref="uToast"></u-toast>
		<image src="/static/login-img.png" mode="aspectFit"  style="width: 300rpx;height: 300rpx;margin: 0 auto;display: block;"></image>
		<u-form @submit="handleLogin" :model="loginForm">
			<text>\n<br/></text>
			<u-text text="FMP Login" color="orange" align="center" size="30"></u-text>
			<text>\n<br/></text>
			<u-form-item>
				<u-input v-model="loginForm.account" placeholder="please input account" />
			</u-form-item>

			<u-form-item>
				<u-input type="password" v-model="loginForm.password" placeholder="password" />
			</u-form-item>
			
			<u-button type="info" block @click="handleLogin" :custom-style="{backgroundColor: 'orange', color: '#fff'}">Login</u-button>
			
			<view class="register-link">
				<u-text @click="goToRegister" text="Not account? Go Register"></u-text>
			</view>
		</u-form>
	</view>


</template>

<script>
	export default {
		options: {
			styleIsolation: 'shared'
		},
		data() {
			return {
				loginForm: {
					account: 'Test1',
					password: '1234567891',
					role: "user"
				}
			};
		},
		methods: {
			handleLogin() {
				// 这里可以加表单验证，确保用户名和密码不为空
				if (!this.loginForm.account) {
					this.$refs.uToast.show({
						type: "error",
						message: "请输入账号"
					});
					return;
				}

				if (!this.loginForm.password) {
					this.$refs.uToast.show({
						type: "error",
						message: "请输入密码"
					});
					return;
				}


				uni.$u.http.post("login/login", this.loginForm).then(res => {
					if (res.code == 200) {
						uni.setStorageSync("token", "Bearer " + res.data.token)
						uni.setStorageSync("userId", res.data.info.id)
						uni.setStorageSync("role", res.data.info.role)
						uni.setStorageSync("nickname", res.data.info.nickname)
						this.$refs.uToast.show({
							type: "success",
							message: "登录成功"
						});
						uni.switchTab({
							url: "/pages/index/index"
						})
					} else {
						this.$refs.uToast.show({
							type: "error",
							message: res.msg
						});
					}

				}).catch(error => {
					console.log(error)
					this.$refs.uToast.show({
						type: "error",
						message: "网络错误"
					});
				})
			},

			goToRegister() {
				// 跳转到注册页面
				uni.navigateTo({
					url: '/pages/regist/regist'
				});
			}
		}
	};
</script>

<style scoped lang="scss">
	.register-link {
		display: block;
		margin-top: 30rpx;
		text-align: right;
		color: #007aff;
		text-decoration: none;
		cursor: pointer;
	}
</style>
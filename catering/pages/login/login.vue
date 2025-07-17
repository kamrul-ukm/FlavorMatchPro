<template>
	<view style="width: 80%;margin: 100rpx auto;">
		<u-toast ref="uToast"></u-toast>
		<image src="/static/login-img.png" mode="aspectFit"  style="width: 300rpx;height: 300rpx;margin: 0 auto;display: block;"></image>
		<u-form @submit="handleLogin" :model="loginForm">
			<text>\n<br/></text>
			<u-text text="FMP Login" color="#ffaa00" align="center" size="30"></u-text>
			<text>\n<br/></text>
			<u-form-item>
				<u-input v-model="loginForm.account" placeholder="please input account" />
			</u-form-item>

			<u-form-item>
				<u-input type="password" v-model="loginForm.password" placeholder="password" />
			</u-form-item>
			
			<u-button type="info" block @click="handleLogin" :custom-style="{backgroundColor: '#ffaa00', color: '#fff'}">Login</u-button>
			
			<view class="register-link">
				<view @click="goToRegister">
					<text style="color: #606266; margin-right: 10px;">No account yet?</text>
					<text style="color: #ffaa00; font-weight: bold;">Register now</text>	
					</view>
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
					account: '',
					password: '',
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
						message: "ENTER ACCOUNT"
					});
					return;
				}

				if (!this.loginForm.password) {
					this.$refs.uToast.show({
						type: "error",
						message: "ENTER PASSWORD"
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
							message: "LOGIN successfully!"
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
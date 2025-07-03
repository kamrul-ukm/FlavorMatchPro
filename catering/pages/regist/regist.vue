<template>
	<view style="width: 80%;margin: 100rpx auto;">
		<u-toast ref="uToast"></u-toast>
		<u-text text="FMP Register" color="orange" align="center" size="30"></u-text>
		<text>\n<br/></text>
		<view style="width: 30%;margin: 0 auto;">
			<u-avatar :src="loginForm.avatar" size="85" />
			<u-button @click="chooseAvatar" type="primary" size="small" style="margin-top: 10px; background-color: orange; border-color: #fff;">Upload</u-button>
		</view>
		<u-form @submit="handleLogin" :model="loginForm">

			<u-form-item label="ACCOUNT" label-width="85">
				<u-input v-model="loginForm.account" placeholder="Enter your ACCOUNT" />
			</u-form-item>

			<u-form-item label="USERNAME" label-width="85">
				<u-input v-model="loginForm.nickname" placeholder="Enter your USERNAME" />
			</u-form-item>

			<u-form-item label="PASSWORD" label-width="85">
				<u-input type="password" v-model="loginForm.password" placeholder="Enter your PASSWORD" />
			</u-form-item>

			<!-- 性别 -->
			<u-form-item label="GENDER" label-width="85">
				<u-radio-group v-model="loginForm.sex">
					<u-radio label="男" name="男">MALE</u-radio>
					<u-radio label="女" name="女">FEMALE</u-radio>
				</u-radio-group>
			</u-form-item>

			<!-- 手机号 -->
			<u-form-item label="PHONE NUMBER" label-width="85" required>
				<u-input v-model="loginForm.phone" placeholder="Enter your PHONE" />
			</u-form-item>

		


			<u-button type="primary" block @click="handleLogin">REGISTER</u-button>

			<view class="register-link">
				<u-text @click="goToRegister" text="Have an account? Log in"></u-text>
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

			chooseAvatar() {
				uni.chooseImage({
					count: 1,
					success: (res) => {
						const file = res.tempFilePaths[0];
						// 上传头像到服务器
						uni.uploadFile({
							url: this.$api + 'file/upload',
							filePath: file,
							name: 'file',
							success: (uploadRes) => {
								const data = JSON.parse(uploadRes.data);
								if (data.code === 200) {

									this.loginForm = {
										...this.user,
										avatar: this.$api + "file/" + data.data,
										img: data.data
									};
									console.log(this.user.avatar)
									uni.showToast({
										title: '头像更新成功',
										icon: 'success'
									});
								}
							}
						});
					}
				});
			},
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


				uni.$u.http.post("login/register", this.loginForm).then(res => {
					if (res.code == 200) {
						this.$refs.uToast.show({
							type: "success",
							message: "注册成功"
						});
						uni.redirectTo({
							url: "/pages/login/login"
						})
					} else {
						this.$refs.uToast.show({
							type: "error",
							message: "注册失败"
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
				// 跳转到登录页面
				uni.navigateTo({
					url: '/pages/login/login'
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
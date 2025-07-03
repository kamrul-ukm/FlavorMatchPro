<template>
	<view class="container">
		<!-- 头像 -->
		<view class="avatar-section">
			<u-avatar :src="user.avatar" size="100" />
			<u-button @click="chooseAvatar" type="primary" size="small" style="margin-top: 10px;">更换头像</u-button>
		</view>

		<!-- 用户信息表单 -->
		<u-form :model="user" @submit="submitForm" labelWidth="120rpx">
			<u-form-item label="ACCOUNT" required>
				<u-input v-model="user.account" disabled />
			</u-form-item>
			<!-- 昵称 -->
			<u-form-item label="USERNAME" label-width="85" required>
				<u-input v-model="user.nickname" placeholder="Enter your USERNAME" />
			</u-form-item>

			<!-- 性别 -->
			<u-form-item label="GENDER" label-width="85" required>
				<u-radio-group v-model="user.sex">
					<u-radio label="男" name="男">MALE</u-radio>
					<u-radio label="女" name="女">FEMALE</u-radio>
				</u-radio-group>
			</u-form-item>

			<!-- 手机号 -->
			<u-form-item label="PHONE NUMBER" label-width="85" required>
				<u-input v-model="user.phone" placeholder="Enter your PHONE" />
			</u-form-item>

	

			<!-- 地址 -->
			<u-form-item label="ADDRESS" label-width="85" required>
				<u-input v-model="user.address" placeholder="Enter your ADDRESS" />
			</u-form-item>


			<!-- 提交按钮 -->
			<u-form-item>
				<u-button type="primary" @click="submitForm">SAVE</u-button>
			</u-form-item>
		</u-form>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {
					id: '',
					account: '',
					nickname: '',
					sex: '',
					phone: '',
					email: '',
					address: '',
					img: '',
					status: '',
					avatar: ''
				}
			}
		},
		onShow() {
			this.getData()
		},
		methods: {
			getData() {
				// 接口获取个人信息并赋值
				uni.$u.http.get("/login/info").then(res => {
					this.user = res.data
					this.user.avatar = this.$api + "file/" + this.user.img
				});
			},

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

									this.user = {
										...this.user,
										avatar: this.$api + "file/" + data.data
									};
									this.user.img = data.data
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

			// 提交修改
			submitForm() {
				uni.$u.http.post("/user", this.user).then(res => {
					if (res.code === 200) {
						uni.showToast({
							title: '信息修改成功',
							icon: 'success'
						});
						uni.setStorageSync("nickname", this.user.nickname)
					} else {
						uni.showToast({
							title: '修改失败',
							icon: 'none'
						});
					}
				});
			}
		}
	}
</script>

<style scoped>
	.container {
		padding: 20px;
	}

	.avatar-section {
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		margin-bottom: 20px;
	}

	u-button {
		margin-top: 10px;
	}

	u-form-item {
		margin-bottom: 20px;
	}
</style>
<template>
	<view>
		<!-- 个人信息展示 -->
		<view class="user-info">
			<view style="width: 200rpx;margin: 0 auto;">
				<u-avatar :src="user.avatar" size="200rpx" />
			</view>

			<text class="nickname">{{ user.nickname }}</text>
		</view>

<text>\n<br/></text>

		<!-- 菜单列表 -->
		<u-list>
			<u-list-item>
				<u-cell title="PERSONAL CENTER" icon="man-add" @click="goToPersonalCenter" />
			</u-list-item>
			<u-list-item>
				<u-cell title="MY ORDER" icon="man-add" @click="goToOrder" />
			</u-list-item>
			<u-list-item>
				<u-cell title="MESSAGE" icon="chat" @click="goToPrivateMessages" />
			</u-list-item>
			<u-list-item>
				<u-cell title="ABOUT US" icon="info-circle" @click="goToAboutUs" />
			</u-list-item>
			<u-list-item>
				<u-cell title="LOG OUT" icon="info-circle" @click="goToLogin" />
			</u-list-item>
		</u-list>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {
					avatar: '', // 个人头像
					nickname: '用户名' // 用户昵称
				}
			};
		},
		onShow() {
			this.getData()
		},
		methods: {
			getData() {
				uni.$u.http.get("/login/info").then(res => {
					this.user = res.data
					this.user.avatar = this.$api + "file/" + this.user.img
					console.log(this.user.avatar)
				});
			},
			// 退出登录
			goToLogin() {
				uni.removeStorageSync("userId")
				uni.removeStorageSync("nickname")
				uni.redirectTo({
					url: '/pages/login/login'
				});
			},
			// 跳转到个人中心
			goToPersonalCenter() {

				uni.navigateTo({
					url: '/pages/my/center'
				});
			},
			goToOrder() {
				uni.navigateTo({
					url: '/pages/orders/orders'
				});
			},
			// 跳转到私信页面
			goToPrivateMessages() {
				uni.navigateTo({
					url: '/pages/my/consulation'
				});
			},
			// 跳转到关于我们页面
			goToAboutUs() {
				uni.navigateTo({
					url: '/pages/my/about'
				});
			}
		}
	};
</script>

<style scoped>
	.user-info {
		text-align: center;
		margin-top: 20px;
	}

	.nickname {
		margin-top: 10px;
		font-size: 18px;
		color: #333;
	}

	u-list-item {
		margin-top: 10px;
	}
</style>
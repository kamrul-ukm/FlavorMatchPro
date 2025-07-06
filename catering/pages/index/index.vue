<template>
	<view>
		<!-- 轮播图 -->
		<u-swiper :list="slideList" keyName="image" showTitle :autoplay="true" circular
			indicatorMode="line"></u-swiper>


		<!-- 美食推荐标题 -->
		<u-text text="New Arrivals" color="#FF8C00" align="center" size="70rpx" margin="20rpx 0"></u-text>

		<!-- 美食推荐列表 -->
		<view v-for="food in foods" :key="food.id" class="food-card">
			<u-image :src="api + 'file/' + food.img" mode="aspectFill" class="food-img" width="120rpx"
				height="120rpx"></u-image>
			<view class="food-info">
				<u-text class="food-title" :text="food.name" type="primary" bold></u-text>
				<u-text :text="food.description" lines="2" size="26rpx" color="#666"></u-text>
				<view class="food-meta">
					<u-text :text="`PRICE: ¥${food.price}`" color="#e64340" size="30rpx"></u-text>
					<u-text :text="`CATEGORY: ${food.category.name}`" size="30rpx" color="#999"></u-text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				slideList: [],
				foods: [],
				api: this.$api
			};
		},
		onShow() {
			this.getData();
		},
		onPullDownRefresh() {
			this.getData();
			uni.stopPullDownRefresh();
		},
		methods: {
			getData() {
				// 获取轮播图
				uni.$u.http.get("/slide").then(res => {
					this.slideList = res.data.map(item => ({
						image: this.$api + "file/" + item.img,
						title: item.name
					}));
				});

				// 获取美食数据
				uni.$u.http.get("/food").then(res => {
					this.foods = res.data;
				});
			}
		}
	};
</script>

<style scoped>
	.food-card {
		display: flex;
		background-color: #fff;
		border-radius: 12rpx;
		margin-bottom: 20rpx;
		padding: 20rpx;
		box-shadow: 0 4rpx 8rpx rgba(0, 0, 0, 0.1);
		align-items: center;
	}

	.food-img {
		width: 120rpx;
		height: 120rpx;
		border-radius: 12rpx;
		margin-right: 20rpx;
	}

	.food-info {
		flex: 1;
		display: flex;
		flex-direction: column;
	}

	.food-title {
		font-size: 18px;
		margin-bottom: 10rpx;
	}

	.food-meta {
		margin-top: 10rpx;
		display: flex;
		justify-content: space-between;
	}
</style>
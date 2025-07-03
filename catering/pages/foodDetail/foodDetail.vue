<template>
	<view class="detail-container">
		<u-image :src="api + 'file/' + food.img" mode="aspectFill" width="100%" height="400rpx"></u-image>
		<view class="detail-info">
			<u-text :text="'名称：' + food.name" size="32rpx" bold></u-text>
			<u-text :text="'描述：' + food.description" size="28rpx" margin="10rpx 0"></u-text>
			<u-text :text="'价格：¥' + food.price" size="28rpx" color="#e64340"></u-text>
			<u-text :text="'分类：' + (food.category ? food.category.remark : '')" size="26rpx" color="#666"></u-text>
			<u-button type="success" size="medium" @click="addToCart">加入购物车</u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				api: this.$api,
				food: {}
			};
		},
		onLoad(options) {
			this.getFoodDetail(options.id);
		},
		methods: {
			getFoodDetail(id) {
				uni.$u.http.get('/food/' + id).then(res => {
					this.food = res.data;
				});
			},
			addToCart() {
				uni.$u.http.post('/cart', {
					foodId: this.food.id,
					quantity: 1
				}).then(() => {
					uni.$u.toast('已加入购物车');
				}).catch(() => {
					uni.$u.toast('添加失败，请稍后重试');
				});
			}
		}
	};
</script>

<style scoped>
	.detail-container {
		padding: 20rpx;
	}

	.detail-info {
		margin-top: 20rpx;
		display: flex;
		flex-direction: column;
		gap: 20rpx;
	}
</style>
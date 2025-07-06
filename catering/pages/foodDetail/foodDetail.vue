<template>
	<view class="detail-container">
		<u-image :src="api + 'file/' + food.img" mode="aspectFill" width="100%" height="400rpx"></u-image>
		<view class="detail-info">
			<u-text :text="'FOOD NAME：' + food.name" color="#3c9cff" size="50rpx" bold></u-text>
			<u-text :text="'DESCRIBE：' + food.description" size="30rpx" margin="10rpx 0"></u-text>
			<u-text :text="'PRICE：RM' + food.price" size="30rpx" color="#e64340"></u-text>
			<u-text :text="'CATEGORY：' + (food.category ? food.category.remark : '')" size="30rpx" color="#666"></u-text>
			<u-button type="success" size="medium" @click="addToCart">ADD TO CART</u-button>
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
					uni.$u.toast('SUCCESS!');
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
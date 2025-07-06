<template>
	<view class="container">
		<u-text text="MY ORDER" color="#ffaa00" align="center"  size="70rpx" margin="20rpx 0" />

		<block v-for="order in orders" :key="order.id">
			<view class="order-card">
				<view class="order-header">
					<text class="order-id">ORDER NUMBER: {{ order.id }}</text>
					<text class="order-status">{{ order.status }}</text>
				</view>

				<view class="order-user">
					<text>USERNAME: {{ order.nickname }}</text>
					<text>PHONE NUMBER: {{ order.phone }}</text>
					<text>ADDRESS: {{ order.address }}</text>
				</view>

				<view class="order-items">
					<block v-for="item in order.orderItemList" :key="item.id">
						<view class="order-item">
							<u-image class="item-img" :src="api + 'file/' + item.food.img" mode="aspectFill"
								width="100rpx" height="100rpx" />
							<view class="item-info">
								<view class="item-name">{{ item.food.name }}</view>
								<view class="item-desc">{{ item.food.description }}</view>
								<view class="item-meta">
									<text>QUANTITY: {{ item.quantity }}</text>
									<text>PRICE: RM{{ item.price.toFixed(2) }}</text>
								</view>
							</view>
						</view>
					</block>
				</view>

				<view class="order-meta">
					<text>PREFERRED TIME: {{ order.sendTime }}</text>
					<text>REMARK: {{ order.remark }}</text>
					<text>ORDER TIME: {{ order.createdAt }}</text>
					<text class="total-price">TOTAL: {{ order.totalPrice.toFixed(2) }}</text>
					<view v-if="order.status === 'Out for delivery'" class="btn-area">
						<u-button type="success" size="medium" shape="circle"
							@click="confirmFinish(order.id)">CONFIRM :D</u-button>
					</view>

				</view>
			</view>
		</block>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				api: this.$api,
				orders: []
			};
		},
		onShow() {
			this.init();
		},
		methods: {
			init() {
				uni.$u.http.get(`/orders`).then(res => {
					this.orders = res.data;
				}).catch(err => {
					console.error("获取订单失败", err);
				});
			},
			confirmFinish(orderId) {
				uni.showModal({
					title: 'WARNING!',
					content: 'Have you received your order?',
					success: (res) => {
						if (res.confirm) {
							uni.$u.http.post(`/orders`, {
								id: orderId,
								status: "COMPLETED"
							}).then(res => {
								if (res.code === 200) {
									this.$u.toast('ORDER COMPLETED');
									this.init(); // 刷新订单列表
								} else {
									this.$u.toast(res.msg || '操作失败');
								}
							}).catch(() => {
								this.$u.toast('网络异常，请稍后再试');
							});
						}
					}
				});
			}

		}
	};
</script>

<style scoped>
	.btn-area {
		margin-top: 30rpx;
		display: flex;
		justify-content: flex-end;
	}

	.container {
		padding: 30rpx;
		background-color: #f7f7f7;
	}

	.order-card {
		background-color: #ffffff;
		margin-bottom: 40rpx;
		padding: 30rpx;
		border-radius: 20rpx;
		box-shadow: 0 6rpx 12rpx rgba(0, 0, 0, 0.05);
	}

	.order-header {
		display: flex;
		justify-content: space-between;
		font-weight: bold;
		font-size: 28rpx;
		margin-bottom: 20rpx;
	}

	.order-status {
		color: #07c160;
	}

	.order-user text,
	.order-meta text {
		display: block;
		margin: 6rpx 0;
		color: #444;
		font-size: 26rpx;
	}

	.order-items {
		margin-top: 20rpx;
	}

	.order-item {
		display: flex;
		margin-bottom: 20rpx;
		border-bottom: 1px solid #eee;
		padding-bottom: 20rpx;
	}

	.item-img {
		border-radius: 12rpx;
		margin-right: 20rpx;
	}

	.item-info {
		flex: 1;
		display: flex;
		flex-direction: column;
	}

	.item-name {
		font-size: 30rpx;
		font-weight: bold;
		color: #333;
	}

	.item-desc {
		font-size: 24rpx;
		color: #777;
		margin: 6rpx 0;
	}

	.item-meta {
		display: flex;
		justify-content: space-between;
		font-size: 26rpx;
		color: #555;
	}

	.order-meta {
		margin-top: 20rpx;
		font-size: 26rpx;
		color: #555;
	}

	.total-price {
		color: #e64340;
		font-weight: bold;
		margin-top: 10rpx;
		font-size: 30rpx;
	}
</style>
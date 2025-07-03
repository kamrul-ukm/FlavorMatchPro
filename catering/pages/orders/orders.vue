<template>
	<view class="container">
		<u-text text="我的订单" size="50rpx" align="center" margin="30rpx 0" />

		<block v-for="order in orders" :key="order.id">
			<view class="order-card">
				<view class="order-header">
					<text class="order-id">订单号：{{ order.id }}</text>
					<text class="order-status">{{ order.status }}</text>
				</view>

				<view class="order-user">
					<text>收货人：{{ order.nickname }}</text>
					<text>电话：{{ order.phone }}</text>
					<text>地址：{{ order.address }}</text>
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
									<text>数量：{{ item.quantity }}</text>
									<text>单价：¥{{ item.price.toFixed(2) }}</text>
								</view>
							</view>
						</view>
					</block>
				</view>

				<view class="order-meta">
					<text>配送时间：{{ order.sendTime }}</text>
					<text>备注：{{ order.remark }}</text>
					<text>下单时间：{{ order.createdAt }}</text>
					<text class="total-price">合计：¥{{ order.totalPrice.toFixed(2) }}</text>
					<view v-if="order.status === '配送中'" class="btn-area">
						<u-button type="success" size="medium" shape="circle"
							@click="confirmFinish(order.id)">确认完成</u-button>
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
					title: '提示',
					content: '确认已完成配送？',
					success: (res) => {
						if (res.confirm) {
							uni.$u.http.post(`/orders`, {
								id: orderId,
								status: "已完成"
							}).then(res => {
								if (res.code === 200) {
									this.$u.toast('订单已完成');
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
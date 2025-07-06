<template>
	<view>

		<u-text text="MY CART" color="#FF8C00" align="center" size="70rpx" margin="20rpx 0" />
		<view v-if="carts.length === 0" class="empty-cart" style="color: red;" align="center">
			<br/>
			. . . EMPTY CART
		</view>

		<view v-else class="cart-container">


			<!-- 全选 -->
			<view class="select-all">
				<checkbox :checked="allChecked" @change="toggleAll" /> ALL
			</view>

			<!-- 购物车列表 -->
			<block v-for="item in carts" :key="item.id">
				<view class="cart-item">
					<checkbox :checked="checkedIds.includes(item.id)" @change="e => toggleItem(item.id)" />
					<u-image class="cart-img" :src="api + 'file/' + item.food.img" mode="aspectFill" width="100rpx"
						height="100rpx" />
					<view class="cart-info">
						<view class="food-name">{{ item.food.name }}</view>
						<view class="food-price">PRICE: RM{{ item.food.price }}</view>
						<view class="food-quantity">
							QUANTITY: 
							<button size="mini" @click="changeQuantity(item, -1)"
								style='width: 40rpx; height: 30rpx; line-height: 30rpx; padding: 0; font-size: 30rpx;'>-</button>
							<text style="margin: 0 10rpx;">{{ item.quantity }}</text>
							<button size="mini" @click="changeQuantity(item, 1)"
								style='width: 40rpx; height: 30rpx; line-height: 30rpx; padding: 0; font-size: 30rpx;'>+</button>
						</view>
						<view class="food-subtotal">SUBTOTAL: RM{{ (item.quantity * item.food.price).toFixed(2) }}</view>
						
					</view>
					<button size="mini" type="warn" @click="deleteItem(item)">DELETE</button>
				</view>
			</block>

			<!-- 结算栏 -->
			<view class="checkout-bar" style="position: relative;">
				<view>TOTAL: RM{{ totalPrice.toFixed(2) }}</view>
				<button type="primary" @click="showPayment = true" :disabled="checkedIds.length === 0" style="position: absolute; right: 0; line-height: 60rpx;">PAY</button>
			</view>

			<!-- 支付弹窗 -->
			<view v-if="showPayment" class="popup-mask">
				<view class="popup-box">
					<u-text text="Scan the QR Code to Pay" bold size="30rpx" align="center"  />
					<image :src="qrCodeUrl" mode="widthFix" style="width: 200rpx" />

					<view class="form-field">
						<u-text text="USERNAME：" bold></u-text>
						<input v-model="nickname" placeholder="ENTER USERNAME" class="input" />
					</view>
					<view class="form-field">
						<u-text text="PHONE NUMBER：" bold></u-text>
						<input v-model="phone" placeholder="ENTER PHONE NUMBER" class="input" />
					</view>
					<view class="form-field">
						<u-text text="ADDRESS：" bold></u-text>
						<input v-model="address" placeholder="ENTER ADDRESS" class="input" />
					</view>
					<view class="form-field">
						<u-text text="REMARK：" bold></u-text>
						<input v-model="remark" placeholder="ENTER REMARK" class="input" />
					</view>

					<view class="form-field">
						<u-text text="Select your preferred time: " bold></u-text>
						<picker mode="selector" :range="timeOptions" @change="onTimeSelect">
							<view class="input">
								{{ sendTime || 'PICK A TIME (Future times only)' }}
							</view>
						</picker>
					</view>

					<view class="popup-buttons" style="text-align: center;width: 100%;display: block;">
						<button  @click="confirmPayment" 
						style="background-color: #007aff; color: #ffffff; line-height: 60rpx; display: inline-block; width: 200rpx; margin: 0;">PAY</button>
						<button @click="showPayment = false" style="line-height: 60rpx;display: inline-block;width:200rpx;margin: 0;">CANCEL</button>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				api: this.$api,
				carts: [],
				checkedIds: [],
				showPayment: false,
				qrCodeUrl: '',
				nickname: "",
				phone: "",
				address: "",
				remark: '',
				sendTime: '',
				timeOptions: []
			};
		},
		computed: {
			totalPrice() {
				return this.carts.reduce((total, item) => {
					if (this.checkedIds.includes(item.id)) {
						return total + item.quantity * item.food.price;
					}
					return total;
				}, 0);
			},
			allChecked() {
				return this.carts.length > 0 && this.checkedIds.length === this.carts.length;
			}
		},
		onShow() {
			this.getData();
			this.updateTimeOptions();
			uni.$u.http.get("/login/info").then(res => {
				this.nickname = res.data.nickname
				this.phone = res.data.phone
				// 如果后台返回的地址为空，则使用 'DAIN IN' 作为默认值
				this.address = res.data.address|| 'DAIN IN'
			});
			uni.$u.http.get("/config/1").then(res => {
				this.qrCodeUrl = this.$api + 'file/' + res.data.img
			});
		},
		methods: {
			updateTimeOptions() {
				const now = new Date();
				const options = [];
				let hour = now.getHours();
				let minute = Math.ceil(now.getMinutes() / 10) * 10;
				if (minute === 60) {
					hour += 1;
					minute = 0;
				}
				for (let h = hour; h < 24; h++) {
					for (let m = (h === hour ? minute : 0); m < 60; m += 10) {
						options.push(`${String(h).padStart(2, '0')}:${String(m).padStart(2, '0')}`);
					}
				}
				this.timeOptions = options;
			},
			onTimeSelect(e) {
				this.sendTime = this.timeOptions[e.detail.value];
			},
			getData() {
				uni.$u.http.get("/cart").then(res => {
					this.carts = res.data;
					this.checkedIds = res.data.map(i => i.id);
				});
			},
			toggleItem(id) {
				if (this.checkedIds.includes(id)) {
					this.checkedIds = this.checkedIds.filter(i => i !== id);
				} else {
					this.checkedIds.push(id);
				}
			},
			toggleAll() {
				this.checkedIds = this.allChecked ? [] : this.carts.map(i => i.id);
			},
			changeQuantity(item, delta) {
				const newQuantity = item.quantity + delta;
				if (newQuantity < 1) {
					uni.$u.toast("At least one item");
					return;
				}
				uni.$u.http.post(`/cart`, {
					quantity: newQuantity,
					id: item.id
				}).then(() => {
					item.quantity = newQuantity;
					uni.$u.toast(":D");
				});
			},
			deleteItem(item) {
				uni.showModal({
					title: "WARNING!",
					content: "Delete this item?",
					success: res => {
						if (res.confirm) {
							uni.$u.http.delete(`/cart/${item.id}`).then(() => {
								this.carts = this.carts.filter(c => c.id !== item.id);
								this.checkedIds = this.checkedIds.filter(id => id !== item.id);
								uni.$u.toast("DELETE successfully!");
							});
						}
					}
				});
			},
			confirmPayment() {
				if (!this.sendTime) {
					uni.$u.toast('Please select your preferred time.');
					return;
				}
				const today = new Date().toISOString().split('T')[0];
				uni.$u.http.post(`/orders/buyCart`, {
					cartIds: this.checkedIds,
					remark: this.remark,
					sendTime: `${today} ${this.sendTime}:00`,
					nickname: this.nickname,
					phone: this.phone,
					address: this.address
				}).then(() => {
					uni.$u.toast('Your service will begin momentarily :D');
					this.showPayment = false;
					this.remark = '';
					this.sendTime = '';
					this.getData();
				});
			}
		}
	};
</script>



<style scoped>
	.cart-container {
		padding: 20rpx;
	}

	.select-all {
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
	}

	.cart-item {
		display: flex;
		background-color: #fff;
		margin-bottom: 20rpx;
		padding: 20rpx;
		border-radius: 12rpx;
		align-items: flex-start;
	}

	.cart-img {
		border-radius: 8rpx;
		margin: 0 20rpx;
	}

	.cart-info {
		flex: 1;
		display: flex;
		flex-direction: column;
		font-size: 26rpx;
	}

	.food-name {
		font-weight: bold;
		margin-bottom: 10rpx;
	}

	.food-price,
	.food-quantity,
	.food-subtotal {
		margin: 6rpx 0;
	}

	.checkout-bar {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-top: 30rpx;
		padding: 20rpx;
		background-color: #f5f5f5;
		border-radius: 12rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.popup-mask {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, 0.4);
		z-index: 1000;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.popup-box {
		background-color: #fff;
		padding: 40rpx;
		border-radius: 16rpx;
		text-align: center;
		width: 600rpx;
	}

	.form-field {
		margin: 6rpx 0;
		text-align: left;
		line-height: 40rpx;
	}

	.input {
		background-color: #f3f3f3;
		padding: 16rpx;
		border-radius: 8rpx;
		margin-top: 10rpx;
	}

	.popup-buttons {
		margin-top: 30rpx;
		display: flex;
		flex-direction: column;
	}
</style>
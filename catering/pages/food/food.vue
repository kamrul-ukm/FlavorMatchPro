<template>
	<view class="container">
		<view class="left-menu">
			<view v-for="(cat, index) in categorysWithAll" :key="cat.id"
				:class="['category-item', currentCategoryId === cat.id ? 'active' : '']"
				@click="changeCategory(cat.id)">
				{{ cat.name }}
			</view>
		</view>

		<view class="right-content">
			<view v-for="food in filteredFoods" :key="food.id" class="food-card">
				<u-image class="food-img" :src="api + 'file/' + food.img" mode="aspectFill" width="120rpx"
					height="120rpx"></u-image>
				<view class="food-info">
					<u-text class="food-title" :text="food.name" type="primary" bold></u-text>
					<view class="food-meta">
						<u-text :text="'PRICE: RM' + food.price" color="#e64340" size="30rpx"></u-text>
						<u-text :text="'CATEGORY: ' + (food.category ? food.category.remark : '')" size="20rpx"
							color="#999"></u-text>
					</view>
					<view class="food-actions">
						<view class="food-actions">
							<u-button type="success" size="mini" :custom-style="{ width: '170px', height: '19px', 'font-size': '12px' }" @click="addToCart(food)">ADD TO CART</u-button>
							<u-button type="primary" size="mini" :custom-style="{ width: '170px', height: '20px', 'font-size': '12px' }" @click="goToDetail(food)">VIEW DETAILS</u-button>
						</view>

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
				foods: [],
				categorys: [],
				currentCategoryId: 0, // 0 表示全部
			};
		},
		computed: {
			// 分类列表 + “全部”
			categorysWithAll() {
				return [{
					id: 0,
					name: 'All',
					remark: 'ALL'
				}, ...this.categorys];
			},
			// 根据当前分类过滤美食
			filteredFoods() {
				if (this.currentCategoryId === 0) return this.foods;
				return this.foods.filter(f => f.categoryId === this.currentCategoryId);
			}
		},
		onShow() {
			this.getData();
		},
		onPullDownRefresh() {
			this.getData();
			uni.stopPullDownRefresh();
		},
		methods: {
			goToDetail(food) {
				uni.navigateTo({
					url: '/pages/foodDetail/foodDetail?id=' + food.id
				});
			},
			changeCategory(id) {
				this.currentCategoryId = id;
			},
			getData() {
				// 分类
				uni.$u.http.get("/category").then(res => {
					this.categorys = res.data;
				});

				// 美食
				uni.$u.http.get("/food").then(res => {
					this.foods = res.data;
				});
			},
			// 添加到购物车方法
			addToCart(food) {
				uni.$u.http.post('/cart', {
					foodId: food.id,
					quantity: 1 // 默认数量为1
				}).then(res => {
					uni.$u.toast('SUCCESS!');
				}).catch(err => {
					uni.$u.toast('添加失败，请稍后重试');
				});
			}
		}
	};
</script>

<style scoped>
	.container {
		display: flex;
		flex-direction: row;
		height: 100vh;
	}

	.left-menu {
		width: 180rpx;
		background-color: #f8f8f8;
		border-right: 1px solid #FF8C00;
		padding-top: 20rpx;
	}

	.category-item {
		padding: 20rpx;
		text-align: center;
		font-size: 28rpx;
		color: #666;
	}

	.category-item.active {
		background-color: #fff;
		font-weight: bold;
		color: #FF8C00;
	}

	.right-content {
		flex: 1;
		padding: 20rpx;
		overflow-y: auto;
	}

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
		border-radius: 12rpx;
		margin-right: 20rpx;
	}

	.food-info {
		flex: 1;
		display: flex;
		flex-direction: column;
	}

	.food-title {
		font-size: 32rpx;
		margin-bottom: 10rpx;
	}

	.food-meta {
		margin-top: 10rpx;
		display: flex;
		justify-content: space-between;
	}
</style>
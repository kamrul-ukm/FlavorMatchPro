<template>
	<view>
		<!-- 聊天区域 -->
		<view class="chat-container" ref="chatContainer">
			<scroll-view :scroll-y="true" scroll-with-animation style="height: 600px;" ref="scrollView">
				<view class="chat-content">
					<view v-for="item in consultation" :key="item.id" class="chat-item">
						<view class="chat-question">
							<view class="chat-bubble left" v-if="item.reply">{{ item.reply }}</view>

						</view>
						<view class="chat-reply">
							<view class="chat-bubble right" v-if="item.question">{{ item.question }}</view>
							<image @tap="playVoice2(item.raw)" v-if="item.raw" src="/static/icon/audio.png"
								style="width: 160rpx;height: 80rpx;"></image>
						</view>
					</view>
				</view>
			</scroll-view>
		</view>

		<!-- 输入框和发送按钮 -->
		<view class="input-container">

			<input v-model="question" class="chat-input" placeholder="请输入消息" />
			<button class="send-button" @click="updateData">发送</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				question: "",
				consultation: [],
				interval: null
			};
		},
		onLoad() {
	
		},
		onShow() {
			this.getData();
			this.interval = setInterval(this.getData, 2000)
		},
		onUnload() {
			if (this.interval) {
				clearInterval(this.interval)
			}
		},
		onHide() {
			if (this.interval) {
				clearInterval(this.interval)
			}
		},
		watch: {
			// 监听聊天数据变化
			consultation(newVal) {
				this.$nextTick(() => {
					this.scrollToBottom(); // 滚动到底部
				});
			}
		},
		methods: {
		
			getData() {
				uni.$u.http.get("/consultation").then(res => {
					this.consultation = res.data
				});
			},

			// 滚动到聊天区域的最底部
			scrollToBottom() {
				const scrollView = this.$refs.scrollView;
				if (scrollView) {
					scrollView.scrollTop = scrollView.scrollHeight;
				}
			},
			// 编辑、添加提交方法
			updateData() {
				if (!this.question.trim()) {
					uni.showToast({
						title: "消息不能为空",
						icon: "none"
					});
					return;
				}

				uni.$u.http.post("/consultation", {
					userId: uni.getStorageSync("userId"),
					question: this.question
				}).then(res => {
					if (res.code === 200) {
						uni.showToast({
							title: '发送成功',
							icon: 'success'
						});
						this.question = ""; // 清空输入框
						this.getData()
					} else {
						uni.showToast({
							title: '修改失败',
							icon: 'none'
						});
					}
				});

			}
		}
	};
</script>

<style scoped>
	.chat-container {
		height: calc(100vh - 200rpx);
		border-radius: 8px;
		overflow: hidden;
		background-color: #f9f9f9;
		padding: 10px;
	}

	.chat-content {
		display: flex;
		flex-direction: column;
		gap: 10px;
	}

	.chat-item {
		display: flex;
		justify-content: space-between;
	}

	.chat-bubble {
		padding: 10px;
		border-radius: 10px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}

	.chat-bubble.left {
		background-color: #d9f7be;
		align-self: flex-start;
	}

	.chat-bubble.right {
		background-color: #91d5ff;
		align-self: flex-end;
	}

	.input-container {
		display: flex;
		justify-content: space-between;
		margin-top: 10px;
		height: 100rpx;
	}

	.chat-input {
		width: 80%;
		padding: 10px;
		border-radius: 10px;
		border: 1px solid #ccc;
	}

	.send-button {
		width: 15%;
		padding: 10px;
		background-color: #007aff;
		color: #fff;
		border-radius: 10px;
		text-align: center;
		line-height: 20px;
	}
</style>
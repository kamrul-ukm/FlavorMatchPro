<template>
	<view class="container">
		<!-- 标题 -->
		<view class="header">
			<text class="title">About us</text>
		</view>

		<!-- 介绍内容 -->
		<view class="content">
			<text class="subtitle">Restaurant Introduction</text>
			<text class="description">
				<br/>We are great.
			</text>

<text>\n<br/></text>
			
			<text class="subtitle">Our service</text>
			<view class="service-list">
				
				<view class="service-item">
					<text class="service-title">Food</text>
					<text class="service-description"><br/>We are very good.</text>
				</view>
				
				<view class="service-item">
					<text class="service-title">App</text>
					<text class="service-description"><br/>We are the best.</text>
				</view>
			</view>

<text><br/></text>

			<text class="subtitle">Contact the Developer</text>
			<text class="description">
				<br/>Email: a185126@siswa.ukm.edu.my
			</text>

<text>\n<br/></text>

			<text class="footer">
				<br/>Thank you for choosing Flover Match Pro, have a nice day!
			</text>
		</view>
	</view>
</template>

<style scoped>
	.container {
		height: 100vh;
		padding: 20px;
		background-color: #f9f9f9;
	}

	.header {
		text-align: center;
		margin-bottom: 20px;
	}

	.title {
		font-size: 24px;
		font-weight: bold;
		color: #333;
	}

	.content {
		margin-top: 20px;
	}

	.subtitle {
		font-size: 18px;
		font-weight: 600;
		color: #333;
		margin-top: 15px;
	}

	.description {
		font-size: 16px;
		color: #666;
		line-height: 1.5;
	}

	.service-list {
		margin-top: 15px;
	}

	.service-item {
		margin-bottom: 10px;
	}

	.service-title {
		font-size: 16px;
		font-weight: 600;
		color: #333;
	}

	.service-description {
		font-size: 14px;
		color: #666;
	}

	.footer {
		margin-top: 20px;
		font-size: 16px;
		color: #666;
		text-align: center;
	}
</style>
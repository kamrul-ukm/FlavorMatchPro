<template>
  <div class="content">
    <div v-html="htmlContent"></div>
  </div>
</template>

<script>
export default {
  name: "showHtml",
  data() {
    return {
      htmlContent: ""
    }
  },
  mounted() {
    const content = localStorage.getItem("htmlContent");
    if (content) {
      this.htmlContent = content;
      localStorage.removeItem("htmlContent");
    }
  }
}
</script>

<style scoped>
.content {
  font-family: Arial, sans-serif;
  font-size: 16px;
  line-height: 1.6;
  color: #333; /* 深灰色，柔和但清晰 */
  background-color: #f9f9f9; /* 淡色背景减少视觉疲劳 */
  padding: 10px;
  border-radius: 5px;
}
</style>

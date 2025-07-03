<template>
  <el-menu class="el-menu-demo" mode="horizontal" background-color="#334157" text-color="#fff" active-text-color="#fff"
    router>
    <el-submenu index="2" class="submenu">
      <template slot="title">{{ nickname }}</template>
      <el-menu-item @click="exit()">退出</el-menu-item>
    </el-submenu>
  </el-menu>
</template>
<script>
export default {
  name: 'navcon',
  data() {
    return {
      nickname: localStorage.getItem("nickname")
    }
  },
  created() {

  },
  methods: {
    // 退出登录
    exit() {
      this.$confirm('退出登录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          setTimeout(() => {
            localStorage.setItem("token", '')
            localStorage.setItem("role", '')
            localStorage.setItem("username", '')
            this.$message({
              type: 'success',
              message: '已退出登录!'
            })
            this.$router.push("/login")
          }, 1000)
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
    },
  }
}
</script>
<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  border: none;
}

.submenu {
  float: right;
}

.buttonimg {
  height: 60px;
  background-color: transparent;
  border: none;
}

.showimg {
  width: 26px;
  height: 26px;
  position: absolute;
  top: 17px;
  left: 17px;
}

.showimg:active {
  border: none;
}
</style>

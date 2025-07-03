<template>
  <div>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" router backgroundColor="#333333"
             text-color="#fff" active-text-color="#ffd04b">
      <el-menu-item>
        <img style="
    height: 60px;
   margin-top: -3px" src="../assets/img/logo.png"/>
      </el-menu-item>
      <el-menu-item index="/front/home">首页</el-menu-item>
      <el-menu-item index="/front/news">通知公告</el-menu-item>
      <el-menu-item index="/front/drone">幼儿托管</el-menu-item>
      <el-menu-item index="/front/droneDispatch">幼儿托管调度</el-menu-item>
      <el-menu-item index="/front/realTimeMonitoring">在线监控</el-menu-item>
      <el-menu-item index="/front/droneReservation">我的预约</el-menu-item>
      <el-menu-item index="/front/center">个人中心</el-menu-item>

      <el-menu-item v-if="nickname == ''" index="/regist" style="float: right;">注册</el-menu-item>
      <el-menu-item v-if="nickname == ''" index="/login" style="float: right;">登录</el-menu-item>

      <el-submenu index="2-4" v-if="nickname != ''" style="float: right;">
        <template slot="title">{{ nickname }}</template>
        <el-menu-item @click="exit()">退出登录</el-menu-item>
      </el-submenu>
    </el-menu>

  </div>
</template>
<script>


export default {
  name: 'navcon',

  data() {
    return {
      activeIndex: '',
      nickname: '',
      dialogVisible: false,
      consultation: [],
      interval: null
    }
  },
  created() {
    this.nickname = localStorage.getItem("nickname")
  },
  methods: {
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

.el-menu {
  background-color: #333333;
}

.submenu {
  float: right;
}
</style>

<template>
  <el-container class="index-con">
    <el-aside class="asideshow">
      <leftnav></leftnav>
    </el-aside>
    <el-container class="main-con">
      <el-header class="index-header">
        <navcon></navcon>
      </el-header>
      <el-main clss="index-main">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import navcon from '../../components/navcon.vue'
import leftnav from '../../components/leftnav.vue'
export default {
  name: 'index',
  data () {
    return {

    }
  },
  // 注册组件
  components: {
    navcon,
    leftnav
  },
  methods: {},
  created () {
    console.log("index index");
  },
  beforeUpdate () { },
  // 挂载前状态(里面是操作)
  beforeMount () {
    // 弹出登录成功
    this.$message({
      message: '登录成功',
      type: 'success'
    })
  }
}
</script>
<style >
.index-con {
  height: 100%;
  width: 100%;
  box-sizing: border-box;
}

.aside {
  width: 64px !important;
  height: 100%;
  background-color: #f5f5f5;
  margin: 0px;
}
.asideshow {
  width: 240px !important;
  height: 100%;
  background-color: #f5f5f5;
  margin: 0px;
}
.index-header,
.index-main {
  padding: 0px;
  border-left: 2px solid #333;
}

</style>

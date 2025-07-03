<template>
  <el-menu default-active="2" :collapse="collapsed" collapse-transition router :default-active="$route.path"
           unique-opened
           class="el-menu-vertical-demo" background-color="#334157" text-color="#fff" active-text-color="#ffd04b">
        <div class="logobox">
          <div>
            <img src="../assets/img/logo.png" width="100px" height="100px"/>
          </div>
          <h4>FMP Backstage Management</h4>
        </div>
    <div v-for="menu in allmenu">
      <el-submenu :key="menu.menuid" :index="menu.menuname" v-if="menu.menus != null">
        <template slot="title">
          <i :class="menu.icon"></i>
          <span>{{ menu.menuname }}</span>
        </template>
        <el-menu-item-group>

          <el-menu-item v-for="chmenu in menu.menus" :index="chmenu.url" :key="chmenu.menuid">
            <i :class="chmenu.icon"></i>
            <span>{{ chmenu.menuname }}</span>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-menu-item v-if="menu.menus == null" :index="menu.url" :key="menu.menuid">
        <i :class="menu.icon"></i>
        <span>{{ menu.menuname }}</span>
      </el-menu-item>
    </div>

  </el-menu>
</template>
<script>
export default {
  name: 'leftnav',
  data() {
    return {
      collapsed: false,
      allmenu: []
    }
  },
  directives: {
    role(el, val) {
      let role = localStorage.getItem("role")
      if (val.value != null && val.value.indexOf(role) < 0) {
        el.style.display = "none"
      }
    }
  },
  // 创建完毕状态(里面是操作)
  created() {
    this.allmenu = this.generateMenuArray(this.$router.options.routes)

  },
  methods: {
    // 遍历路由生成菜单数组
    generateMenuArray(routes) {
      let menuArray = [];
      routes.forEach(route => {
        if (route.path == "/admin") {
          route.children.forEach(child => {
            menuArray.push({
              menuid: Math.floor(Math.random() * 100), // 随机生成一个menuid
              icon: child.icon || "",
              menuname: child.name || "",
              url: ("/admin/" + child.path) || "",
              role: child.role
            });
          });
        }
      })
      return menuArray;
    }
  }
}
</script>
<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 240px;
  min-height: 400px;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  border: none;
  text-align: left;
}

.el-menu-item-group__title {
  padding: 0px;
}

.el-menu-bg {
  background-color: #1f2d3d !important;
}

.el-menu {
  border: none;
  height: 100%;
}

.logobox {
  height: 150px;
  line-height: 40px;
  color: #9d9d9d;
  font-size: 17px;
  text-align: center;
  padding: 20px 0px;
}

</style>

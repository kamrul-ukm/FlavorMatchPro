<template>
  <div class="login-wrap">
    <el-form label-position="left" :model="loginForm"  label-width="0px" :rule="rules" ref="loginForm"
             class="demo-ruleForm login-container">
      <h3 class="title"> Backstage Management </h3>
      <el-form-item prop="username">
        <el-input type="text" v-model="loginForm.account" auto-complete="off" placeholder="Account"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="Password"></el-input>
      </el-form-item>

      <el-form-item style="width:100%;">
        <el-button type="primary" style="width:100%; background-color: #FF8C00; border-color: #FF8C00;" @click="submitForm()" :loading="logining">Login</el-button>
      </el-form-item>
    </el-form>


  </div>
</template>
<script type="text/ecmascript-6">

export default {
  name: 'login',
  components: {},
  data() {
    return {
      logining: false,
      loginForm: {
        account: '',
        password: '',
        role:"admin"
      },
      rules: {
        account: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur'}
        ]
      }
    }
  },
  created() {


  },
  methods: {
    submitForm() {
      this.$refs.loginForm.validate((valid) => {
        if (!valid) {
          this.$message.error("表单验证失败，请检查数据");
          return;
        }
        this.logining = true
        this.$axios.post("/login/login", {
          account: this.loginForm.account,
          password: this.loginForm.password,
          role: this.loginForm.role
        }).then(res => {
          if (res.code == 200) {
            this.logining = false
            localStorage.setItem("token", res.data.token)
            localStorage.setItem("userId", res.data.info.id)
            localStorage.setItem("account", res.data.info.account);
            localStorage.setItem("nickname", res.data.info.nickname);
            localStorage.setItem("role", res.data.info.role);
            this.$axios.defaults.headers["Authorization"] =
              "Bearer " + localStorage.getItem("token");
            let that = this;
            that.$router.push({path: '/admin/home'})
          } else {
            this.$message({
              message: '请检查账号或者密码',
              type: 'warning'
            })
            this.logining = false;
          }
        }, err => {
          this.$message({
            message: '登陆失败,请检查网络问题',
            type: 'warning'
          })
          this.logining = false;
        })
      })

    },
  }
}
</script>

<style scoped>
.regist-link {
  text-decoration: none;
  color: gray;
}
.login-wrap {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  padding-top: 10%;
  background-image: url("../assets/img/bg.png");
  background-repeat: no-repeat;
  background-position: center right;
  background-size: cover;
}

.login-container {
  border-radius: 10px;
  margin: 0px auto;
  width: 350px;
  padding: 30px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  text-align: left;
  box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
}

.title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

.remember {
  margin: 0px 0px 35px 0px;
}

.code-box {
  text-align: right;
}

.codeimg {
  height: 40px;
}
</style>

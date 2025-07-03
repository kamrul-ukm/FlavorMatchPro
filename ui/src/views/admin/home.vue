<template>
  <div class="dashboard-container">
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
      <el-breadcrumb-item :to="{ path: '/' }">Index</el-breadcrumb-item>
      <el-breadcrumb-item>Home</el-breadcrumb-item>
    </el-breadcrumb>


    <!-- 个人信息卡片 -->
    <el-card class="profile-card">
      <h3 class="card-title">个人信息</h3>
      <el-row :gutter="20">
        <el-col :span="6" class="avatar-container">
          <el-upload class="avatar-uploader" :action="$api + 'file/upload'" :show-file-list="false" :on-success="handleAvatarSuccess">
            <img v-if="editForm.img" :src="$api + 'file/' + editForm.img" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-col>
        <el-col :span="18">
          <el-descriptions title="用户信息" :column="2" border>
            <el-descriptions-item label="用户名">{{ editForm.account }}</el-descriptions-item>
            <el-descriptions-item label="昵称">{{ editForm.nickname }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{ editForm.sex }}</el-descriptions-item>
            <el-descriptions-item label="电话">{{ editForm.phone }}</el-descriptions-item>
          </el-descriptions>
          <el-button type="primary" class="edit-btn" @click="editMode = !editMode">编辑信息</el-button>
        </el-col>
      </el-row>

      <!-- 编辑表单 -->
      <el-form v-if="editMode" label-width="130px" :model="editForm" class="edit-form">
        <el-form-item label="昵称">
          <el-input size="small" v-model="editForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input size="small" v-model="editForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input size="small" v-model="editForm.sex"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input size="small" v-model="editForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input size="small" v-model="editForm.remark"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateData()">保存修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      editMode: false,
      editForm: {
        id: 0,
        account: "",
        nickname: "",
        password: "",
        sex: "",
        dept: "",
        phone: "",
        role: "",
        remark: "",
        img: ''
      }
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.$set(this.editForm, "img", res.data);
    },
    getData() {
      this.$axios.get(`/login/info`).then(res => {
        if (res.code === 200) {
          this.editForm = res.data;
        } else {
          this.$message.info("获取数据失败");
        }
      });
    },
    updateData() {
      this.$axios.post("/user", this.editForm).then(res => {
        if (res.code === 200) {
          this.getData();
          this.$message.success("修改成功！");
          this.editMode = false;
        } else {
          this.$message.info("修改失败");
        }
      }).catch(() => {
        this.$message.error("操作失败，请稍后再试！");
        this.editMode = false;
      });
    }
  }
};
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.breadcrumb {
  margin-bottom: 20px;
}

.info-row {
  margin-bottom: 20px;
}

.info-card, .chart-card, .profile-card {
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}

.card-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.avatar-container {
  text-align: center;
}

.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

.chart-container {
  width: 100%;
  height: 400px;
}

.edit-btn {
  margin-top: 15px;
}

.edit-form {
  margin-top: 20px;
}
</style>

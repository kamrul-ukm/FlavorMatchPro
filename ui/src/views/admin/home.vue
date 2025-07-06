<template>
  <div class="dashboard-container">
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
      <el-breadcrumb-item :to="{ path: '/' }">INDEX</el-breadcrumb-item>
      <el-breadcrumb-item>Home</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 个人信息卡片 -->
    <el-card class="profile-card">
      <h3 class="card-title">PERSONAL INFORMATION</h3>
      <el-row :gutter="20">
        <el-col :span="6" class="avatar-container">
          <el-upload class="avatar-uploader" :action="$api + 'file/upload'" :show-file-list="false" :on-success="handleAvatarSuccess">
            <img v-if="editForm.img" :src="$api + 'file/' + editForm.img" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-col>
        <el-col :span="18">
          <el-descriptions title="DETAILS" :column="2" border>
            <el-descriptions-item label="ACCOUNT">{{ editForm.account }}</el-descriptions-item>
            <el-descriptions-item label="USERNAME">{{ editForm.nickname }}</el-descriptions-item>
            <el-descriptions-item label="GENDER">{{ editForm.sex }}</el-descriptions-item>
            <el-descriptions-item label="PHONE">{{ editForm.phone }}</el-descriptions-item>
          </el-descriptions>
          <el-button type="primary" class="edit-btn" @click="editMode = !editMode">EDIT</el-button>
        </el-col>
      </el-row>

      <!-- 编辑表单 -->
      <el-form v-if="editMode" label-width="130px" :model="editForm" class="edit-form">
        <el-form-item label="USERNAME">
          <el-input size="small" v-model="editForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="PASSWORD">
          <el-input size="small" v-model="editForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="GENDER">
          <el-input size="small" v-model="editForm.sex"></el-input>
        </el-form-item>
        <el-form-item label="PHONE">
          <el-input size="small" v-model="editForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="REMARK">
          <el-input size="small" v-model="editForm.remark"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateData()">SAVE</el-button>
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
          this.$message.success("SAVED successfully!");
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

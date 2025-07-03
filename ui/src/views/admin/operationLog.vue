<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{ this.$route.name }}</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="searchForm" class="search-form">
      <el-form-item label="">
        <el-input size="small" v-model="searchForm.username" placeholder="输入名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索
        </el-button>
      </el-form-item>
    </el-form>

    <!--按钮-->
    <el-row style="margin: 10px auto">
      <el-button type="success" size="mini" @click=update() icon="el-icon-plus">新增</el-button>
      <el-button size="mini" @click="deleteBatch()" icon="el-icon-delete" type="danger">删除</el-button>
    </el-row>

    <!--列表-->
    <el-table size="small" :data="tableData" highlight-current-row v-loading="loading" border
              element-loading-text="拼命加载中" @selection-change="handleSelectionChange"
              style="width: 100%;">

      <el-table-column
        type="selection"
        width="55">
      </el-table-column>


      <el-table-column align="center" sortable prop="username" label="名字">
      </el-table-column>


      <el-table-column align="center" sortable prop="operation" label="操作内容">
      </el-table-column>
      <el-table-column align="center" sortable prop="method" label="请求方法">
      </el-table-column>
      <el-table-column align="center" sortable prop="params" label="请求参数">
      </el-table-column>
      <el-table-column align="center" sortable prop="ip" label="IP地址">
      </el-table-column>
      <el-table-column align="center" sortable prop="createTime" label="时间">
      </el-table-column>


      <el-table-column label="操作" min-width=120>
        <template slot-scope="scope">

          <el-button size="mini" @click="deleteData(scope.row.id)" icon="el-icon-delete" type="danger">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
      style="margin-top: 10px"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :page-size="pageSize"
      :current-page="currentPage"
      @size-change="pageSizeChange"
      @current-change="currentPageChange"
      :page-sizes="[5, 10, 20, 50]"
      :total="total">
    </el-pagination>


  </div>
</template>

<script>

export default {
  data() {
    return {
      tableData: [],
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      // 编辑与添加
      editForm: {},
      // 请求数据参数
      searchForm: {
        title: ''
      },
      currentPage: 1,
      pageSize: 10,
      total: 10,
      multipleSelection: [],
      rules: {
        name: [
          {required: true, message: '请输入', trigger: 'blur'},
          {min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur'}
        ],
      },
      categorys: []
    }
  },

  /**
   * 创建完毕
   */
  created() {
    this.getData()
    this.init();
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    init() {
      this.$axios.get(`/category`).then(res => {
        this.categorys = res.data
      })
    },
    deleteBatch() {
      let ids = this.multipleSelection.map(item => item.id)
      this.$axios.post("/operationLog/del/batch", ids)
        .then(res => {
          this.loading = false
          if (res.code == 200) {
            this.editFormVisible = false;
            this.getData()
            this.$message({
              type: 'success',
              message: '操作成功！'
            })
          } else {
            this.$message({
              type: 'info',
              message: res.msg
            })
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('操作失败，请稍后再试！')
          this.editFormVisible = false;
        })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val)
    },
    handleAvatarSuccess(res, file) {
      this.$set(this.editForm, "img", res.data)
    },
    // 关闭弹出框
    closeDialog() {
      this.editFormVisible = false
    },
    // 获取数据方法
    getData() {
      this.loading = true
      this.$axios.get(`/operationLog/page?pageNum=${this.currentPage}&pageSize=${this.pageSize}&title=${this.searchForm.title}`).then(res => {
        this.loading = false
        if (res.code != 200) {
          this.$message({
            type: 'info',
            message: "获取数据失败"
          })
        } else {
          this.tableData = res.data.records
          // 分页赋值
          this.currentPage = res.data.current
          this.pageSize = res.data.size
          this.total = res.data.total
        }
      })
    },
    // 分页插件事件
    pageSizeChange(n) {
      this.pageSize = n
      this.getData()
    },
    currentPageChange(n) {
      this.currentPage = n
      this.getData()
    },
    update(row) {
      if (row != null) {
        this.editForm = JSON.parse(JSON.stringify(row));
      } else {
        this.editForm = {}
      }
      this.editFormVisible = true;
    },
    //搜索事件
    search() {
      this.getData()
    },
    // 编辑、添加提交方法
    updateData() {
      this.$refs.editForm.validate((valid) => {
        if (!valid) {
          this.$message.error("表单验证失败，请检查数据");
          return;
        }
        this.editForm.categoryName = this.categorys.find(item => item.id == this.editForm.categoryId).name
        this.$axios.post("/operationLog", this.editForm)
          .then(res => {
            this.loading = false
            if (res.code == 200) {
              this.editFormVisible = false;
              this.getData()
              this.$message({
                type: 'success',
                message: '操作成功！'
              })
            } else {
              this.$message({
                type: 'info',
                message: res.msg
              })
            }
          })
          .catch(err => {
            this.loading = false
            this.$message.error('操作失败，请稍后再试！')
            this.editFormVisible = false;
          })
      });
    },

    deleteData(id) {
      this.$axios.delete("/operationLog/" + id)
        .then(res => {
          this.loading = false
          if (res.code == 200) {
            this.getData()
            this.$message({
              type: 'success',
              message: '操作成功！'
            })
          } else {
            this.$message({
              type: 'info',
              message: "操作失败"
            })
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('操作失败，请稍后再试！')
        })


    }
  }
}
</script>

<style scoped>
.search-form {
  margin-top: 20px;
}

/* 全局搜索框优化 */
.search-form {
  margin-top: 20px;
  display: flex;
  align-items: center;
  gap: 10px;
}

/* 统一按钮间距 */
.el-row {
  margin: 10px auto;
  display: flex;
  gap: 8px;
}

/* 按钮优化 */
.el-button {
  border-radius: 4px; /* 轻微圆角 */
  transition: all 0.3s ease-in-out;
}

.el-button:hover {
  opacity: 0.85;
}

/* 表格优化 */
.el-table {
  border-radius: 4px;
  overflow: hidden;
  background-color: #fff;
}

.el-table th {
  background-color: #f5f5f5 !important;
  font-weight: bold;
}

.el-table td {
  text-align: center;
}

/* 分页样式 */
.el-pagination {
  margin-top: 10px;
  text-align: center;
}

/* 对话框优化 */
.el-dialog {
  border-radius: 8px;
}

.dialog-footer {
  text-align: right;
}
</style>



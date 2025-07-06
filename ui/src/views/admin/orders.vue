<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">INDEX</el-breadcrumb-item>
      <el-breadcrumb-item>{{ this.$route.name }}</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 搜索 -->
    <el-form :inline="true" :model="searchForm" class="search-form">
      <el-form-item>
        <el-input size="small" v-model="searchForm.nickname" placeholder="输入昵称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">SEARCH</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格 -->
    <el-table size="small" :data="tableData" v-loading="loading" border style="width: 100%;">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="USERNAME" prop="nickname" align="center"></el-table-column>
      <el-table-column label="PHONE" prop="phone" align="center"></el-table-column>
      <el-table-column label="ADDRESS" prop="address" align="center"></el-table-column>
      <el-table-column label="TOTAL" prop="totalPrice" align="center"></el-table-column>
      <el-table-column label="STATE" prop="status" align="center"></el-table-column>
      <el-table-column label="ORDER TIME" prop="createdAt" align="center"></el-table-column>
      <el-table-column label="DELIVERY TIME" prop="sendTime" align="center"></el-table-column>
      <el-table-column label="REMARK" prop="remark" align="center"></el-table-column>

      <el-table-column label="OPERATE" align="center" min-width="100">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" icon="el-icon-view" @click="detail(scope.row)"
                     style="padding: 7px 25px;">DETAIL</el-button>
          <br/>
          <el-button
            size="mini"
            type="success"
            icon="el-icon-truck"
            @click="deliver(scope.row)"
            style="padding: 7px 15px;"
            v-if="scope.row.status === 'PAID'">DELIVERY
          </el-button>
          <br/><br/>
          <el-button size="mini" @click="deleteData(scope.row.id)" icon="el-icon-delete" type="danger"
                     style="padding: 7px 21px;">DELETE
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
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

    <!-- 弹窗：订单详情 -->
    <el-dialog title="订单详情" :visible.sync="editFormVisible" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="USERNAME">{{ editForm.nickname }}</el-descriptions-item>
        <el-descriptions-item label="PHONE NUMBER">{{ editForm.phone }}</el-descriptions-item>
        <el-descriptions-item label="ADDRESS" :span="2">{{ editForm.address }}</el-descriptions-item>
        <el-descriptions-item label="REMARK" :span="2">{{ editForm.remark }}</el-descriptions-item>
        <el-descriptions-item label="ORDER TIME">{{ editForm.createdAt }}</el-descriptions-item>
        <el-descriptions-item label="DELIVERY TIME">{{ editForm.sendTime }}</el-descriptions-item>
        <el-descriptions-item label="STATE">{{ editForm.status }}</el-descriptions-item>
        <el-descriptions-item label="TOTAL">￥{{ editForm.totalPrice }}</el-descriptions-item>
      </el-descriptions>

      <el-table
        :data="editForm.orderItemList || []"
        size="small"
        style="margin-top: 20px"
        border>
        <el-table-column label="FOOD NAME" prop="food.name" align="center" />
        <el-table-column label="PRICE" prop="price" align="center" />
        <el-table-column label="QUANTITY" prop="quantity" align="center" />
        <el-table-column label="SUBTOTAL" align="center">
          <template slot-scope="scope">
            ￥{{ (scope.row.quantity * scope.row.price).toFixed(2) }}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
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
      editForm: {
      },
      // 请求数据参数
      searchForm: {
        nickname: ''
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

      }
    }
  },

  /**
   * 创建完毕
   */
  created() {
    this.getData()
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    deliver(row) {
      this.$confirm(`Mark as Out for Delivery？`, 'WARNING!', {
        confirmButtonText: 'SURE',
        cancelButtonText: 'CANCEL',
        type: 'warning'
      }).then(() => {
        this.$axios.post(`/orders`, {id: row.id, status: 'Out for delivery'}).then(res => {
          if (res.code === 200) {
            this.$message.success("DONE!");
            this.getData(); // 刷新数据
          } else {
            this.$message.error(res.msg || "操作失败");
          }
        }).catch(() => {
          this.$message.error("请求失败");
        });
      }).catch(() => {
          this.$message.info("!CANCEL");
      });
    },
    deleteBatch() {
      let ids = this.multipleSelection.map(item => item.id)
      this.$axios.post("/orders/del/batch", ids)
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
      this.$axios.get(`/orders/page?pageNum=${this.currentPage}&pageSize=${this.pageSize}&nickname=${this.searchForm.nickname}`)
        .then(res => {
          this.loading = false
          if (res.code === 200) {
            this.tableData = res.data.records
            this.currentPage = res.data.current
            this.pageSize = res.data.size
            this.total = res.data.total
          } else {
            this.$message.error("获取数据失败")
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
    detail(row) {
      this.editForm = row
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
        this.$axios.post("/orders", this.editForm)
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
      this.$axios.delete("/orders/" + id)
        .then(res => {
          this.loading = false
          if (res.code == 200) {
            this.getData()
            this.$message({
              type: 'success',
              message: 'DELETED successfully!'
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
.el-descriptions__label {
  font-weight: bold;
}

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



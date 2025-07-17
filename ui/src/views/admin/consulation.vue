<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">INDEX</el-breadcrumb-item>
      <el-breadcrumb-item>{{ this.$route.name }}</el-breadcrumb-item>
    </el-breadcrumb>



    <!-- 列表 -->
    <el-table size="small" :data="tableData" highlight-current-row v-loading="loading" border
              element-loading-text="拼命加载中" @selection-change="handleSelectionChange"
              style="width: 100%;margin-top: 10px">

      <el-table-column
        type="selection"
        width="55">
      </el-table-column>

      <el-table-column align="center" sortable prop="user.nickname" label="USERNAME"></el-table-column>

      <el-table-column align="center" sortable prop="question" label="Latest News"></el-table-column>

      <el-table-column align="center" sortable prop="status" label="STATE"></el-table-column>

      <el-table-column label="OPERATE" min-width=120>
        <template slot-scope="scope">
          <el-button size="mini" @click="update(scope.row)" icon="el-icon-edit" type="primary">REPLY</el-button>

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

    <!-- 编辑界面 -->
    <el-dialog title="REPLY DETAIL" :visible.sync="editFormVisible" width="60%" @close="closeDialog">
      <div class="chat-container" ref="chatContainer">
        <div class="chat-content">
          <div v-for="item in editForm.consultations" :key="item.id" class="chat-item">
            <div class="chat-question">
              <div class="chat-bubble left" v-if="item.question">{{ item.question }}</div>
              <div class="chat-bubble left" v-if="item.raw">
                <audio controls style="height: 30px;">
                  <source src="audioImg" >
                </audio>
              </div>
            </div>
            <div class="chat-reply">
              <div class="chat-bubble right" v-if="item.reply">{{ item.reply }}</div>
            </div>
          </div>
        </div>
      </div>

      <el-form label-width="130px"   >
        <el-form-item label="REPLY IN HERE">
          <el-input size="small" v-model="reply" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">CANCEL</el-button>
        <el-button size="small" type="primary" :loading="loading" @click="updateData()">SEND</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import audioImg from "../../assets/icon/audio.png"
export default {
  data() {
    return {
      audioImg: audioImg,
      reply:"",
      tableData: [],
      loading: false,
      editFormVisible: false,
      editForm: { consultations: [] },
      searchForm: { name: '' },
      currentPage: 1,
      pageSize: 10,
      total: 0,
      multipleSelection: [],
      rules: {
        reply: [
          { required: true, message: '请输入回复内容', trigger: 'blur' },
          { min: 1, message: '长度至少为1个字符', trigger: 'blur' }
        ]
      },
      interval:null
    };
  },
  created() {
    this.getData()
    this.init();
    this.interval = setInterval(this.getData, 2000)
  },
  destroyed() {
    clearInterval(this.interval)
  },
  watch: {
    // 监听聊天数据变化
    "editForm.consultations": function () {
      this.scrollToBottom();
    },
  },
  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    init() {

    },
    deleteBatch() {
      let ids = this.multipleSelection.map(item => item.id)
      this.$axios.post("/consultation/del/batch", ids)
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
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.chatContainer;
        if (container) {
          container.scrollTop = container.scrollHeight; // 滚动到底部
        }
      });
    },
    // 获取数据方法
    getData() {
      this.loading = true
      this.$axios.get(`/consultation/page?pageNum=${this.currentPage}&pageSize=${this.pageSize}&title=${this.searchForm.title}`).then(res => {
        this.loading = false
        if (res.code != 200) {

        } else {
          this.tableData = res.data.records
          if (this.editForm.userId) {
            this.editForm = this.tableData.find(item => item.userId == this.editForm.userId)
          }

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

        if (!this.reply) {
          this.$message.error("Message cannot be empty!");
          return;
        }

        let editForm = {
          userId: this.editForm.userId,
          reply: this.reply
        }
        this.$axios.post("/consultation", editForm)
          .then(res => {
            this.loading = false
            if (res.code == 200) {
              this.getData()
              this.reply = ""
              this.$message({
                type: 'success',
                message: 'SENT successfully!'
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
          })

    },

    deleteData(id) {
      this.$axios.delete("/consultation/" + id)
        .then(res => {
          this.loading = false
          if (res.code == 200) {
            this.getdata(this.formInline)
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
};
</script>

<style scoped>
.chat-container {
  height: 400px; /* 固定高度 */
  border: 1px solid #ccc; /* 可选的边框样式 */
  border-radius: 8px; /* 可选的圆角样式 */
  overflow-y: auto; /* 启用垂直滚动 */
  padding: 10px; /* 内容间距 */
  background-color: #f9f9f9; /* 背景颜色 */
}

.chat-content {
  display: flex;
  flex-direction: column;
  gap: 10px; /* 每条聊天项的间距 */
}

.chat-item {
  display: flex;
  justify-content: space-between;
}

.chat-bubble {
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.chat-bubble.left {
  background-color: #d9f7be; /* 左边消息背景颜色 */
  align-self: flex-start; /* 对齐到左边 */
}

.chat-bubble.right {
  background-color: #91d5ff; /* 右边消息背景颜色 */
  align-self: flex-end; /* 对齐到右边 */
}

</style>

<template>
  <div>
    <h4 class="lighter">
      <i
        class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"
      ></i>
      <router-link to="/business/course" class="pink">
        {{ course.name }}
      </router-link>
    </h4>

    <hr />
    <p>
      <router-link
        to="/business/course"
        class="btn btn-white btn-default btn-round"
      >
        <i class="ace-icon fa fa-arrow-left"></i>
        返回课程
      </router-link>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <pagination ref="pagination" v-bind:list="list"></pagination>

    <table id="simple-table" class="table table-bordered table-hover">
      <thead>
        <tr>
          <th>ID</th>
          <th>名称</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="chapter in chapters">
          <td>{{ chapter.id }}</td>
          <td>{{ chapter.name }}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button
                v-on:click="toSection(chapter)"
                class="btn btn-white btn-xs btn-info btn-round"
              >
                小节</button
              >&nbsp;
              <button
                v-on:click="edit(chapter)"
                class="btn btn-white btn-xs btn-info btn-round"
              >
                编辑</button
              >&nbsp;
              <button
                v-on:click="del(chapter.id)"
                class="btn btn-white btn-xs btn-warning btn-round"
              >
                删除
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 模态框 -->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <!-- 表单 -->
            <form class="form-horizontal">
              <div class="form-group">
                <label for="name" class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input
                    v-model="chapter.name"
                    type="text"
                    class="form-control"
                    id="name"
                    placeholder="名称"
                  />
                </div>
              </div>
              <div class="form-group">
                <label for="courseID" class="col-sm-2 control-label"
                  >课程</label
                >
                <div class="col-sm-10">
                  <p class="form-control-static">{{ course.name }}</p>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">
              取消
            </button>
            <button v-on:click="save()" type="button" class="btn btn-primary">
              保存
            </button>
          </div>
        </div>
        <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
    </div>

    <!-- /.modal -->
  </div>
</template>>


<script>
import Pagination from "../../components/pagination.vue";

export default {
  name: "chapter",
  components: { Pagination },

  data: function () {
    return {
      // 映射表单数据
      chapter: {},
      chapters: [],
      course: {},
    };
  },

  // 页面初始化加载方法
  mounted: function () {
    this.$parent.activeSidebar('business-course-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
    if (Tool.isEmpty(course)) {
      _this.$router.push("/welcome");
    }
    _this.course = course;
    _this.list(1);
  },
  methods: {
    // 编辑大章
    edit(chapter) {
      let _this = this;
      // _this.chapter = chapter;
      _this.chapter = $.extend({}, chapter);
      // 禁止点击空白的地方关闭
      $(".modal").modal({
        backdrop: "static",
      });
      $("#form-modal").modal("show");
    },
    // 删除大章
    del(id) {
      let _this = this;

      Confirm.show("删除大章后不可恢复，确认删除？", function () {
        Loading.show();
        _this.$ajax
          .delete(
            process.env.VUE_APP_SERVER + "/business/admin/chapter/delete/" + id
          )
          .then((response) => {
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
              _this.list(1);
              Toast.success("删除成功！");
            }
          });
      });
    },
    // 新增大章
    add() {
      let _this = this;
      // 禁止点击空白的地方关闭
      _this.chapter = {};
      $(".modal").modal({
        backdrop: "static",
      });
      $("#form-modal").modal("show");
    },

    // 获取所有大章的数据
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post(process.env.VUE_APP_SERVER + "/business/admin/chapter/list", {
          page: page,
          //  跟组子组件获取  size应该有一个默认的参数
          size: _this.$refs.pagination.size,
          courseId: _this.course.id,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.chapters = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    // 保存大章的数据
    save(page) {
      let _this = this;
      // 保存校验 非空检验和长度检验
      if (
        !Validator.require(_this.chapter.name, "名称") ||
        // !Validator.require(_this.chapter.courseId, '课程ID') ||
        !Validator.length(_this.chapter.courseId, "课程ID", 1, 8)
      ) {
        return;
      }

      _this.chapter.courseId = _this.course.id;

      Loading.show();
      _this.$ajax
        .post(
          process.env.VUE_APP_SERVER + "/business/admin/chapter/save",
          _this.chapter
        )
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            // 关闭模态框
            $("#form-modal").modal("hide");
            _this.list(1);
            Toast.success("保存成功");
          } else {
            Toast.warning(resp.message);
          }
        });
    },

    /**
     * 点击【小节】
     */
    toSection(chapter) {
      let _this = this;
      SessionStorage.set(SESSION_KEY_CHAPTER, chapter);
      _this.$router.push("/business/section");
    },
  },
};
</script>
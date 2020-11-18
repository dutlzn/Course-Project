<template>
  <div>
    <p>
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
          <th>课程ID</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="chapter in chapters">
          <td>{{ chapter.id }}</td>
          <td>{{ chapter.name }}</td>
          <td>{{ chapter.courseId }}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button v-on:click="edit(chapter)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>
              <button
                v-on:click="del(chapter.id)"
                class="btn btn-xs btn-danger"
              >
                <i class="ace-icon fa fa-trash-o bigger-120"></i>
              </button>
            </div>

            <div class="hidden-md hidden-lg">
              <div class="inline pos-rel">
                <button
                  class="btn btn-minier btn-primary dropdown-toggle"
                  data-toggle="dropdown"
                  data-position="auto"
                >
                  <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                </button>

                <ul
                  class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close"
                >
                  <li>
                    <a
                      href="#"
                      class="tooltip-info"
                      data-rel="tooltip"
                      title="View"
                    >
                      <span class="blue">
                        <i class="ace-icon fa fa-search-plus bigger-120"></i>
                      </span>
                    </a>
                  </li>

                  <li>
                    <a
                      href="#"
                      class="tooltip-success"
                      data-rel="tooltip"
                      title="Edit"
                    >
                      <span class="green">
                        <i
                          class="ace-icon fa fa-pencil-square-o bigger-120"
                        ></i>
                      </span>
                    </a>
                  </li>

                  <li>
                    <a
                      href="#"
                      class="tooltip-error"
                      data-rel="tooltip"
                      title="Delete"
                    >
                      <span class="red">
                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                      </span>
                    </a>
                  </li>
                </ul>
              </div>
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
                  >课程id</label
                >
                <div class="col-sm-10">
                  <input
                    v-model="chapter.courseId"
                    type="text"
                    class="form-control"
                    id="courseID"
                    placeholder="课程id"
                  />
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
    };
  },

  // 页面初始化加载方法
  mounted: function () {
    // this.$parent.activeSidebar('business-chapter-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
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
      _this.$ajax
        .delete(
          "http://127.0.0.1:9000/business/admin/chapter/delete/" + id
        )
        .then((response) => {
          let resp = response.data;
          if (resp.success) {
            // 关闭模态框
            // $("#form-modal").modal("hide");
            _this.list(1);
          }
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
      _this.$ajax
        .post("http://127.0.0.1:9000/business/admin/chapter/list", {
          page: page,
          //  跟组子组件获取  size应该有一个默认的参数
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          let resp = response.data;
          _this.chapters = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    // 保存大章的数据
    save(page) {
      let _this = this;
      _this.$ajax
        .post(
          "http://127.0.0.1:9000/business/admin/chapter/save",
          _this.chapter
        )
        .then((response) => {
          let resp = response.data;
          if (resp.success) {
            // 关闭模态框
            $("#form-modal").modal("hide");
            _this.list(1);
          }
        });
    },
  },
};
</script>
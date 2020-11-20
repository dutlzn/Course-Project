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
                            <th>id</th>
                <th>标题</th>
                <th>课程</th>
                <th>大章</th>
                <th>视频</th>
                <th>时长</th>
                <th>收费</th>
                <th>顺序</th>
                <th>创建时间</th>
                <th>修改时间</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="section in sections">
                <td>{{ section.id }} </td>
                <td>{{ section.title }} </td>
                <td>{{ section.courseId }} </td>
                <td>{{ section.chapterId }} </td>
                <td>{{ section.video }} </td>
                <td>{{ section.time }} </td>
                <td>{{ section.charge }} </td>
                <td>{{ section.sort }} </td>
                <td>{{ section.createdAt }} </td>
                <td>{{ section.updatedAt }} </td>

          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button v-on:click="edit(section)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>
              <button
                v-on:click="del(section.id)"
                class="btn btn-xs btn-danger">
                <i class="ace-icon fa fa-trash-o bigger-120"></i>
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
                        <label for="id" class="col-sm-2 control-label">id</label>
                        <div class="col-sm-10">
                          <input
                            v-model="section.id"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="id"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="title" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-10">
                          <input
                            v-model="section.title"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="标题"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="courseId" class="col-sm-2 control-label">课程</label>
                        <div class="col-sm-10">
                          <input
                            v-model="section.courseId"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="课程"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="chapterId" class="col-sm-2 control-label">大章</label>
                        <div class="col-sm-10">
                          <input
                            v-model="section.chapterId"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="大章"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="video" class="col-sm-2 control-label">视频</label>
                        <div class="col-sm-10">
                          <input
                            v-model="section.video"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="视频"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="time" class="col-sm-2 control-label">时长</label>
                        <div class="col-sm-10">
                          <input
                            v-model="section.time"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="时长"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="charge" class="col-sm-2 control-label">收费</label>
                        <div class="col-sm-10">
                          <input
                            v-model="section.charge"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="收费"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="sort" class="col-sm-2 control-label">顺序</label>
                        <div class="col-sm-10">
                          <input
                            v-model="section.sort"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="顺序"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="createdAt" class="col-sm-2 control-label">创建时间</label>
                        <div class="col-sm-10">
                          <input
                            v-model="section.createdAt"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="创建时间"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="updatedAt" class="col-sm-2 control-label">修改时间</label>
                        <div class="col-sm-10">
                          <input
                            v-model="section.updatedAt"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="修改时间"
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
  name: "section",
  components: { Pagination },

  data: function () {
    return {
      // 映射表单数据
      section: {},
      sections: [],
    };
  },

  // 页面初始化加载方法
  mounted: function () {
    // this.$parent.activeSidebar('business-section-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
  },
  methods: {
    // 编辑小节
    edit(section) {
      let _this = this;
      // _this.section = section;
      _this.section = $.extend({}, section);
      // 禁止点击空白的地方关闭
      $(".modal").modal({
        backdrop: "static",
      });
      $("#form-modal").modal("show");
    },
    // 删除小节
    del(id) {
      let _this = this;

      Confirm.show("删除小节后不可恢复，确认删除？", function () {
        Loading.show();
        _this.$ajax
          .delete(process.env.VUE_APP_SERVER + "/business/admin/section/delete/" + id)
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
    // 新增小节
    add() {
      let _this = this;
      // 禁止点击空白的地方关闭
      _this.section = {};
      $(".modal").modal({
        backdrop: "static",
      });
      $("#form-modal").modal("show");
    },

    // 获取所有小节的数据
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post(process.env.VUE_APP_SERVER + "/business/admin/section/list", {
          page: page,
          //  跟组子组件获取  size应该有一个默认的参数
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.sections = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    // 保存小节的数据
    save(page) {
      let _this = this;
      // 保存校验 非空检验和长度检验

      Loading.show();
      _this.$ajax
        .post(
          process.env.VUE_APP_SERVER + "/business/admin/section/save",
          _this.section
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
  },
};
</script>
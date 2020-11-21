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
        <th>名称</th>
        <th>概述</th>
        <th>时长</th>
        <th>价格（元）</th>
        <th>封面</th>
        <th>级别</th>
        <th>收费</th>
        <th>状态</th>
        <th>报名数</th>
        <th>顺序</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="course in courses">
        <td>{{course.id}}</td>
        <td>{{course.name}}</td>
        <td>{{course.summary}}</td>
        <td>{{course.time}}</td>
        <td>{{course.price}}</td>
        <td>{{course.image}}</td>
        <!-- <td>{{course.level}}</td> -->
          <td>{{COURSE_LEVEL | optionKV(course.level)}}</td>
            <td>{{COURSE_CHARGE | optionKV(course.charge)}}</td>
              <td>{{COURSE_STATUS | optionKV(course.status)}}</td>
        <!-- <td>{{course.charge}}</td> -->
        <!-- <td>{{course.status}}</td> -->
        <td>{{course.enroll}}</td>
        <td>{{course.sort}}</td>

          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button v-on:click="edit(course)" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>
              <button
                v-on:click="del(course.id)"
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
                        <label for="name" class="col-sm-2 control-label">名称</label>
                        <div class="col-sm-10">
                          <input
                            v-model="course.name"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="名称"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="summary" class="col-sm-2 control-label">概述</label>
                        <div class="col-sm-10">
                          <input
                            v-model="course.summary"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="概述"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="time" class="col-sm-2 control-label">时长</label>
                        <div class="col-sm-10">
                          <input
                            v-model="course.time"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="时长"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="price" class="col-sm-2 control-label">价格（元）</label>
                        <div class="col-sm-10">
                          <input
                            v-model="course.price"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="价格（元）"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="image" class="col-sm-2 control-label">封面</label>
                        <div class="col-sm-10">
                          <input
                            v-model="course.image"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="封面"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="level" class="col-sm-2 control-label">级别</label>
                        <div class="col-sm-10">
                    <select v-model="course.level" class="form-control">
                            <option v-for="o in COURSE_LEVEL" v-bind:value="o.key"> {{o.value}}</option>
                          </select>
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="charge" class="col-sm-2 control-label">收费</label>
                        <div class="col-sm-10">
                       <select v-model="course.charge" class="form-control">
                            <option v-for="o in COURSE_CHARGE" v-bind:value="o.key"> {{o.value}}</option>
                          </select>
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="status" class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-10">
                              <select v-model="course.status" class="form-control">
                            <option v-for="o in COURSE_STATUS" v-bind:value="o.key"> {{o.value}}</option>
                          </select>
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="enroll" class="col-sm-2 control-label">报名数</label>
                        <div class="col-sm-10">
                          <input
                            v-model="course.enroll"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="报名数"
                          />
                        </div>
                      </div>
                  <div class="form-group">
                        <label for="sort" class="col-sm-2 control-label">顺序</label>
                        <div class="col-sm-10">
                          <input
                            v-model="course.sort"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="顺序"
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
  name: "business-course",
  components: { Pagination },

  data: function () {
    return {
      // 映射表单数据
      course: {},
      courses: [],
      COURSE_LEVEL:COURSE_LEVEL,
      COURSE_STATUS:COURSE_STATUS,
      COURSE_CHARGE:COURSE_CHARGE

    };
  },

  // 页面初始化加载方法
  mounted: function () {
    // this.$parent.activeSidebar('business-course-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
  },
  methods: {
    // 编辑课程
    edit(course) {
      let _this = this;
      // _this.course = course;
      _this.course = $.extend({}, course);
      // 禁止点击空白的地方关闭
      $(".modal").modal({
        backdrop: "static",
      });
      $("#form-modal").modal("show");
    },
    // 删除课程
    del(id) {
      let _this = this;

      Confirm.show("删除课程后不可恢复，确认删除？", function () {
        Loading.show();
        _this.$ajax
          .delete(process.env.VUE_APP_SERVER + "/business/admin/course/delete/" + id)
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
    // 新增课程
    add() {
      let _this = this;
      // 禁止点击空白的地方关闭
      _this.course = {};
      $(".modal").modal({
        backdrop: "static",
      });
      $("#form-modal").modal("show");
    },

    // 获取所有课程的数据
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post(process.env.VUE_APP_SERVER + "/business/admin/course/list", {
          page: page,
          //  跟组子组件获取  size应该有一个默认的参数
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.courses = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    // 保存课程的数据
    save(page) {
      let _this = this;
     // 保存校验
        if (1 != 1
          || !Validator.require(_this.course.name, "名称")
          || !Validator.length(_this.course.name, "名称", 1, 50)
          || !Validator.length(_this.course.summary, "概述", 1, 2000)
          || !Validator.length(_this.course.image, "封面", 1, 100)
        ) {
          return;
        }


      Loading.show();
      _this.$ajax
        .post(
          process.env.VUE_APP_SERVER + "/business/admin/course/save",
          _this.course
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
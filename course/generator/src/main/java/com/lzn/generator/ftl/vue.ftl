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
            <#list fieldList as field>
                <th>${field.nameCn}</th>
            </#list>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="${domain} in ${domain}s">
            <#list fieldList as field>
                <td>{{ ${domain}.${field.nameHump} }} </td>
            </#list>

          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button v-on:click="edit(${domain})" class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>
              <button
                v-on:click="del(${domain}.id)"
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

              <#list fieldList as field>
                  <div class="form-group">
                        <label for="${field.nameHump}" class="col-sm-2 control-label">${field.nameCn}</label>
                        <div class="col-sm-10">
                          <input
                            v-model="${domain}.${field.nameHump}"
                            type="text"
                            class="form-control"
                            id="name"
                            placeholder="${field.nameCn}"
                          />
                        </div>
                      </div>
                </#list>

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
  name: "${domain}",
  components: { Pagination },

  data: function () {
    return {
      // 映射表单数据
      ${domain}: {},
      ${domain}s: [],
    };
  },

  // 页面初始化加载方法
  mounted: function () {
    // this.$parent.activeSidebar('${module}-${domain}-sidebar');
    let _this = this;
    _this.$refs.pagination.size = 5;
    _this.list(1);
  },
  methods: {
    // 编辑${tableNameCn}
    edit(${domain}) {
      let _this = this;
      // _this.${domain} = ${domain};
      _this.${domain} = $.extend({}, ${domain});
      // 禁止点击空白的地方关闭
      $(".modal").modal({
        backdrop: "static",
      });
      $("#form-modal").modal("show");
    },
    // 删除${tableNameCn}
    del(id) {
      let _this = this;

      Confirm.show("删除${tableNameCn}后不可恢复，确认删除？", function () {
        Loading.show();
        _this.$ajax
          .delete(process.env.VUE_APP_SERVER + "/${module}/admin/${domain}/delete/" + id)
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
    // 新增${tableNameCn}
    add() {
      let _this = this;
      // 禁止点击空白的地方关闭
      _this.${domain} = {};
      $(".modal").modal({
        backdrop: "static",
      });
      $("#form-modal").modal("show");
    },

    // 获取所有${tableNameCn}的数据
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax
        .post(process.env.VUE_APP_SERVER + "/${module}/admin/${domain}/list", {
          page: page,
          //  跟组子组件获取  size应该有一个默认的参数
          size: _this.$refs.pagination.size,
        })
        .then((response) => {
          Loading.hide();
          let resp = response.data;
          _this.${domain}s = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        });
    },

    // 保存${tableNameCn}的数据
    save(page) {
      let _this = this;
      // 保存校验 非空检验和长度检验

      Loading.show();
      _this.$ajax
        .post(
          process.env.VUE_APP_SERVER + "/${module}/admin/${domain}/save",
          _this.${domain}
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
<!-- 小节页面 -->
<template>
	<v-app class="ma-3" app>
		<v-dialog v-model="dialog" persistent max-width="300">
			<v-card>
				<v-card-title>
					表单
				</v-card-title>
				
				<v-card-text>
					 <#list fieldList as field>
							<#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt">
								<#if field.enums>
						<v-col cols="12">
							<v-select label=${field.nameCn} v-model="${domain}.${field.nameHump}" :items="${field.enumsConst}" item-text="value" item-value="key"></v-select>
						</v-col>
						
								<#else>
						<v-col cols="12">
							<v-text-field label=${field.nameCn} v-model="${domain}.${field.nameHump}" required></v-text-field>
						</v-col>
								</#if>
							</#if>
						</#list>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn @click="dialog = false" clas="info">
						取消
					</v-btn>

					<v-btn @click="save()" class="success">
						保存
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>


		<p class="ma-10">

			<v-btn class="mr-5 mb-5" color="primary" @click="add()">
				<v-icon left small>add</v-icon>
				新增
			</v-btn>

			<v-btn class="mb-5" color="primary" @click="list(1)">
				<v-icon left small>refresh</v-icon>
				刷新
			</v-btn>

		</p>


		<pagination ref="pagination" v-bind:list="list" v-bind:itemCount="5"></pagination>


		<v-container>
			<v-row>
				<v-col cols="12">
					<v-simple-table>
						<thead>
							<tr>
								<#list fieldList as field>
									<#if field.nameHump!="createdAt" && field.nameHump!="updatedAt">
										<th class="primary--text text-h6 text-center">${field.nameCn}</th>
									</#if>
								</#list>
								<th class="primary--text  text-h6 text-center">
									操作
								</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="${domain} in ${domain}s" class="text-center">

								<#list fieldList as field>
									<#if field.nameHump!="createdAt" && field.nameHump!="updatedAt">
										<#if field.enums>
											<td>{{${field.enumsConst} | optionKV(${domain}.${field.nameHump})}}</td>
											<#else>
												<td>{{${domain}.${field.nameHump}}}</td>
										</#if>
									</#if>
								</#list>

								<td>
									<v-row align="center" justify="space-around">
										<v-btn x-small fab @click="edit(${domain})" class="primary">
											<v-icon>edit</v-icon>
										</v-btn>

										<v-btn x-small fab @click="del(${domain}.id)" class="warning">
											<v-icon>delete</v-icon>
										</v-btn>
									</v-row>
								</td>
							</tr>
						</tbody>
					</v-simple-table>
				</v-col>
			</v-row>
		</v-container>

	</v-app>
</template>

<script>
	import Pagination from "../../components/pagination.vue";
	export default {
		name: "${module}-${domain}",
		components: {
			Pagination
		},

		data: function() {
			return {
	        ${domain}: {},
	        ${domain}s: [],
	        <#list fieldList as field>
	        <#if field.enums>
						${field.enumsConst}: ${field.enumsConst}_ARRARY,
	        </#if>
	        </#list>
					
					dialog: false,
			}
		},

	  mounted: function() {
			let _this = this;
			_this.$refs.pagination.size = 5;
			_this.list(1);

	  },

		methods: {
			// 获取所有小节的数据
			list(page) {
					let _this = this;
					Loading.show();
					_this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/list', {
						page: page,
						size: _this.$refs.pagination.size,
					}).then((response)=>{
						Loading.hide();
						let resp = response.data;
						_this.${domain}s = resp.content.list;
						_this.$refs.pagination.render(page, resp.content.total);

					})
			},
			/**
			 * 新增小节
			 */
			add() {
        let _this = this;
        _this.${domain} = {};
				_this.dialog = true;
			},

			/**
			 * 编辑小节
			 */
			edit(${domain}) {
        let _this = this;
        _this.${domain} = $.extend({}, ${domain});
				_this.dialog = true;
      },

			/**
			 * 保存
			 */
			save() {
        let _this = this;

        // 保存校验
        if (1 != 1
        <#list fieldList as field>
          <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
            <#if !field.nullAble>
          || !Validator.require(_this.${domain}.${field.nameHump}, "${field.nameCn}")
            </#if>
            <#if (field.length > 0)>
          || !Validator.length(_this.${domain}.${field.nameHump}, "${field.nameCn}", 1, ${field.length?c})
            </#if>
          </#if>
        </#list>
        ) {
          return;
        }

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/save', _this.${domain}).then((response)=>{
          Loading.hide();
          let resp = response.data;
          if (resp.success) {
            _this.dialog = false,
            _this.list(1);
            Toast.success("保存成功！");
          } else {
            Toast.warning(resp.message)
          }
        })
      },

			/**
			 * 删除小节
			 */
      del(id) {
        let _this = this;
        Confirm.show("删除${tableNameCn}后不可恢复，确认删除？", function () {
          Loading.show();
          _this.$ajax.delete(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/delete/' + id).then((response)=>{
            Loading.hide();
            let resp = response.data;
            if (resp.success) {
              _this.list(1);
              Toast.success("删除成功！");
            }
          })
        });
      }
		}
	}
</script>

<style>
</style>

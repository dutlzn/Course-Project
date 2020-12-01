<template>
	<div>
		<button style="margin-bottom: 5px;" v-on:click="selectFile()" type="button" class="btn btn-white btn-default btn-round">
			<i class="ace-icon fa fa-upload">
				{{ text }}
			</i>
		</button>


		<input ref="file" class="hidden" v-bind:id="inputId+'-input'" type="file" v-on:change="uploadFile()">
	</div>
</template>

<script>
	export default {
		name: 'file',
		props: {
			text: {
				default: "上传文件"
			},
			inputId: {
				default: "file-upload"
			},
			suffixs: {
				default: []
			},
			afterUpload: {
				type: Function,
				default: null
			}
		},
		data: function() {
			return {

			};
		},
		methods: {

			uploadFile() {
				let _this = this;
				let formData = new window.FormData();
				// key: "file"必须和后端controller参数名一样
				let file = _this.$refs.file.files[0];
				// 判断文件格式
				let suffixs = _this.suffixs;
				let fileName = file.name;
				let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();

				let validateSuffix = false;
				for (let i = 0; i < suffixs.length; ++i) {
					if (suffixs[i].toLowerCase() === suffix) {
						validateSuffix = true;
						break;
					}
				}

				if (!validateSuffix) {
					Toast.warning("文件格式不正确,只支持上传:" + suffixs.join(","));
					$("#" + _this.inputId + "-input").val("");
					return;
				}

				formData.append('file', file);
				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', formData).then((response) => {
					Loading.hide();
					let resp = response.data;
					console.log("上传文件成功:", resp);
					_this.afterUpload(resp);
					
					// 清空，下次在选择同一个东西时候，也会触发
					$("#" + _this.intputId + "-input").val("");
					

				})
			},

			selectFile() {
				let _this = this;
        $("#" + _this.inputId + "-input").trigger("click");
			}
		}
	}
</script>

<style>
</style>

<template>
	<div>

		<v-col cols="3">
			<v-btn v-on:click="selectFile()" class="warning">
				<!-- <v-icon>upload</v-icon> -->
				{{ text }}
			</v-btn>
		</v-col>

		<input ref="file" v-bind:id="inputId+'-input'" type="file" v-on:change="uploadFile()">
		<!-- 	 <v-file-input

				ref="file"
			  v-bind:id="inputId+'-input'" type="file" v-on:change="uploadFile()"
		  ></v-file-input> -->
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
			use: {
				default: ""
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


				// console.log(JSON.stringify(file));
				/*
				  name: "test.mp4"
				  lastModified: 1901173357457
				  lastModifiedDate: Tue May 27 2099 14:49:17 GMT+0800 (中国标准时间) {}
				  webkitRelativePath: ""
				  size: 37415970
				  type: "video/mp4"
				*/
			 
			 // 文件标识
        let key = hex_md5(file);
        let key10 = parseInt(key, 16);
        let key62 = Tool._10to62(key10);
				console.log(key, key10, key62);


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

				// 文件分片
				let shardSize = 20 * 1024 * 1024; // 20mb为一个分片
				let shardIndex = 2; //分片索引 1标识第一个分片
				let start = (shardIndex-1)* shardSize; // 当前分片起始位置
				let end = Math.min(file.size, start + shardSize); //当前分片结束位置
				let fileShard = file.slice(start, end);
				let size = file.size;
				let shardTotal = Math.ceil(size / shardSize); // 总片数



				// console.log(fileShard);
				// key: "shard" 必须和后端controller参数名一致
				formData.append('shard', fileShard);
				formData.append('shardIndex', shardIndex);
				formData.append('shardSize', shardSize);
				formData.append('shardTotal', shardTotal);
				formData.append('use', _this.use);
				formData.append('name', file.name);
				formData.append('suffix', suffix);
				formData.append('size', size);
				formData.append('key', key62);

				Loading.show();
				_this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', formData).then((response) => {
					Loading.hide();
					let resp = response.data;
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

<style scoped>
	input {
		/* 设计透明度 */
		opacity: 0;

	}
</style>

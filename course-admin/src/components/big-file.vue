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
				let shardIndex = 1; //分片索引 1标识第一个分片
				// let start = (shardIndex - 1) * shardSize; // 当前分片起始位置
				// let end = Math.min(file.size, start + shardSize); //当前分片结束位置
				// let fileShard = file.slice(start, end);
				// let fileShard = _this.getFileShard(shardIndex, shardSize);
				let size = file.size;
				let shardTotal = Math.ceil(size / shardSize); // 总片数
				let param = {
					'shardIndex': shardIndex,
					'shardSize': shardSize,
					'shardTotal': shardTotal,
					// 'shard': base64,
					'use': _this.use,
					'name': file.name,
					'suffix': suffix,
					'size': file.size,
					'key': key62
				};



				// console.log(fileShard);
				// key: "shard" 必须和后端controller参数名一致

				_this.upload(param);

			},

			upload: function(param) {
				let _this = this;
				let shardIndex = param.shardIndex;
				let shardTotal = param.shardTotal;
				let shardSize = param.shardSize;
				let fileShard = _this.getFileShard(shardIndex, shardSize);
				// 将图片转为base64进行传输
				let fileReader = new FileReader();
				// event listener
				fileReader.onload = function(e) {
					let base64 = e.target.result;
					// console.log("base64:", base64);

					param.shard = base64;

					Loading.show();
					_this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', param).then((response) => {
						Loading.hide();
						let resp = response.data;
						if (shardIndex < shardTotal) {
							// 下一个分片
							param.shardIndex = param.shardIndex + 1;
							_this.upload(param);
						} else {
							_this.afterUpload(resp);
							$("#" + _this.intputId + "-input").val("");
						}
						// 清空，下次在选择同一个东西时候，也会触发
						// $("#" + _this.intputId + "-input").val("");
					});

				};
				fileReader.readAsDataURL(fileShard);
			},

			getFileShard(shardIndex, shardSize) {
				let _this = this;
				let file = _this.$refs.file.files[0];
				let start = (shardIndex - 1) * shardSize; // 当前分片起始位置
				let end = Math.min(file.size, start + shardSize); //当前分片结束位置

				let fileShard = file.slice(start, end);
				return fileShard;
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

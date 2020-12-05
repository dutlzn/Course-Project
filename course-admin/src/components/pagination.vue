<template>
	<v-container>
		
		<v-row align="center">
			<v-col cols="8">
				<v-btn small v-bind:disabled="page===1" @click="selectPage(1)" class=" ma-1">
					1
				</v-btn>
				
				<v-btn small  v-bind:disabled="page===1" @click="selectPage(page-1)"  class="ma-1">
					上一页
				</v-btn>
				
				<v-btn small v-for="(p , i ) in pages" v-bind:id="'page-' + p"  :key="i"    class="ma-1"  v-bind:class="{'btn-primary active':page == p}"   @click="selectPage(p)">
					{{ p }}
				</v-btn>
				
				<v-btn  small v-bind:disabled="page===pageTotal" @click="selectPage(page+1)"  class="ma-1">
					下一页
				</v-btn>
				
				<v-btn small v-bind:disabled="page === pageTotal" @click="selectPage(pageTotal)"  class="ma-1">
					{{ pageTotal || 1 }}
				</v-btn>
			</v-col>
			
			<v-col cols="4">
				<v-select v-model="size" :items="items" label="每页条数" no-data-text = "5"></v-select>
			</v-col>
		</v-row>

		&nbsp;

	</v-container>
</template>


<script>
  export default {
    name: 'pagination',
    props: {
      list: {
        type: Function,
        default: null
      },
      itemCount: Number // 显示的页码数，比如总共有100页，只显示10页，其它用省略号表示
    },
		
    data: function () {
      return {
				items: [1, 5, 0, 50, 100],
        total: 0, // 总行数
        size: 1, // 每页条数
        page: 0, // 当前页码
        pageTotal: 0, // 总页数
        pages: [], // 显示的页码数组
      }
    },
    methods: {
      /**
       * 渲染分页组件
       * @param page
       * @param total
       */
      render(page, total) {
        let _this = this;
        _this.page = page;
        _this.total = total;
        _this.pageTotal = Math.ceil(total / _this.size);
        _this.pages = _this.getPageItems(_this.pageTotal, page, _this.itemCount || 5);
      },

      /**
       * 查询某一页
       * @param page
       */
      selectPage(page) {
        let _this = this;
        if (page < 1) {
          page = 1;
        }
        if (page > _this.pageTotal) {
          page = _this.pageTotal;
        }
        if (this.page !== page) {
          _this.page = page;
          if (_this.list) {
            _this.list(page);
          }
        }
      },

      /**
       * 当前要显示在页面上的页码
       * @param total
       * @param current
       * @param length
       * @returns {Array}
       */
      getPageItems(total, current, length) {
        let items = [];
        if (length >= total) {
          for (let i = 1; i <= total; i++) {
            items.push(i);
          }
        } else {
          let base = 0;
          // 前移
          if (current - 0 > Math.floor((length - 1) / 2)) {
            // 后移
            base = Math.min(total, current - 0 + Math.ceil((length - 1) / 2)) - length;
          }
          for (let i = 1; i <= length; i++) {
            items.push(base + i);
          }
        }
        return items;
      }
    }
  }
</script>


<style scoped>
  .btn-primary.active {
    background-color: #2f7bba !important;
    border-color: #27689d !important;
    color: white !important;
    font-weight: 600;
  }
</style>

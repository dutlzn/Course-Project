<template>
    <div>
        <h1>您好，欢迎进入控制台</h1>
    </div>
</template>>

<script>
export default {
    name: 'welcome',
    // 页面初始化加载
    mounted: function() {
        this.$parent.activeSidebar('welcome-sidebar');
    },

    
    methods: {

    }
}
</script>
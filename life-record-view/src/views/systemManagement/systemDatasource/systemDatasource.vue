<template>
    <div>
        <p>数据源管理</p>

        <Search
                ref= "searchBar"
                placeholder="请输入开关名称或开关描述"
                enterButton="搜索"
                size="large"
                onSearch={this.inputSearch}
                allowClear
        />

    </div>
</template>

<script>
    export default {
        name: "systemDatasource"
    }
</script>

<style scoped>

</style>
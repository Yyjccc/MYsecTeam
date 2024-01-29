<template>
    <!-- 渲染单级菜单 -->
    <el-menu-item v-if="!hasChildren(data)" :index="data.url || data.id + ''">
        <el-icon v-if="data.icon">
            <!-- 动态组件 -->
            <component :is="data.icon"></component>
        </el-icon>
        <template #title>
            <span>{{ data.title }}</span>
        </template>
    </el-menu-item>


    <!-- 多级菜单 -->
    <el-sub-menu v-else :index="data.url || data.id + ''">
        <el-icon v-if="data.icon" slot="title" class="erricon">
            <!-- 动态组件 -->
            <component :is="data.icon"></component>
        </el-icon>
        <template #title>
            <span style="text-align: center;">{{ data.title }}</span>
        </template>

        <!-- 使用递归 -->
        <template v-if="data.children">
            <MenuItem v-for="child in data.children" :key="child.id" :data="child" />
        </template>

    </el-sub-menu>
</template>


<script>

export default {
    name: 'MenuItem',
    props: {
        data: {
            type: Object,
            required: true,
            default: () => ({}),
        },
    },
    setup() {
        function hasChildren(item) {
            return item.children && item.children.length;
        }
        return {
            hasChildren,
        }
    },
}


</script>
<style>
.erricon {
    position: relative;
    top: 0px;
}
</style>
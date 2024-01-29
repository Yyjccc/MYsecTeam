<template>
    <div class="header">
        <!-- 左侧 -->
        <div class="header_left">
            <!-- 展开收缩按钮 -->
            <ExpandToggle />
            <!-- 面包屑 -->
            <Breadcrumb />

        </div>

        <!-- 右侧 -->
        <div class="header_right">
            <Search />
            <Notify />
            <FullScreen />
            <ThemeToggle />
            <avator />
            <UserDropDown />
        </div>

        <!-- tag标签-->
        <div class="header_tag">
            <Tag />
        </div>
    </div>
</template>

<script setup>
import ExpandToggle from './ExpandToggle.vue';
import Breadcrumb from './Breadcrumb.vue';
import Search from './Search.vue';
import Notify from './Notify.vue'
import FullScreen from './FullScreen.vue';
import ThemeToggle from './ThemeToggle.vue';
import UserDropDown from './UserDropDown.vue';
import avator from './avator.vue';
import Tag from './Tag.vue'
</script>

<style>
.header {
    display: flex;
    justify-content: space-between;
    width: 100%;
    flex-wrap: wrap;
    position: relative;
    padding: 0 20px;
}

.header_left {
    display: flex;
    align-items: center;
}

.header_right {
    display: flex;
    align-items: center;
}

.header_tag {
    position: absolute;
    left: 0;
    bottom: -53px;
    width: 100%;
    height: 36px;
    background-color: var(--el-bg-color);
    border-bottom: 0.5px solid;
    border-top: 1px solid var(--el-bg-color);
    display: flex;
    align-items: center;
    padding: 5px;
    /* box-shadow: 0 1px 3px 0 rgb(0 0 0 /12%), 0 0 3px rgb(0 0 0 /4%); */
}

.el-header {
    border-bottom: 1px solid;
}
</style>
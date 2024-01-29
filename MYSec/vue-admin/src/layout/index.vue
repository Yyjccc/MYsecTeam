<template>
    <el-container class="common-layout">
        <!-- 侧边导航 -->
        <el-aside :width="$store.state.layoutStore.isExpand ? '200px' : '5px'">
            <Aside />
        </el-aside>
        <el-container>
            <!-- 头部地区 -->
            <el-header>
                <Header />
            </el-header>
            <!-- 内容主区域 -->
            <el-main>
                <Main />
            </el-main>
        </el-container>
    </el-container>
</template>

<script setup>
import Aside from "../components/Aside/index.vue"
import Header from "../components/Header/index.vue"
import Main from "../components/Main.vue"

</script>

<style>
.common-layout {
    height: 100vh;
}

.el-aside {
    transition: width 0.3s ease-out;

    .logo {
        height: 60px;
        line-height: 60px;
        color: #fff;
        font-size: 26px;
        font-weight: bold;
        text-align: center;
    }
}

.el-menu-vertical-dcdemo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
}

.el-header {
    display: flex;
    align-items: center;
    margin-bottom: 34px;
    padding: 0;
}

.el-main {
    width: 100%;
    padding: 0;
}
</style>
<template>
    <div class="breadcrumb">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item class="breadcrumbitem" v-for="item in list" :key="item">{{ item }}</el-breadcrumb-item>

        </el-breadcrumb>
    </div>
</template>
<script setup>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import common from '../../common/common.js';

const route = useRoute();


const list = computed(() => {
    return common.findName(route.path);
})



</script>
<style>
.breadcrumb {
    margin-left: 10px;
}

.breadcrumbitem {
    font-size: 16px;
    font-weight: 500;
}
</style>
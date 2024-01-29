<template>
    <el-scrollbar height="100%" :wrap-style="{
        background: '#2d3a4b',
    }">
        <!-- logo -->
        <div class="logo">
            MYSec
        </div>
        <Menu />

    </el-scrollbar>
</template>

<script setup>
import Menu from './Menu.vue';


</script>
<template>
    <div class="search">
        <el-tooltip effect="dark" content="搜索" placement="bottom">
            <el-icon>
                <Search />
            </el-icon>
        </el-tooltip>
    </div>
</template>
<script setup>
</script>

<style>
.search {
    cursor: pointer;
    width: 38px;
    height: 100%;
    padding: 0 10px;
    box-sizing: border-box;
    display: flex;
    justify-content: center;
    align-items: center;

    &:hover {
        background-color: #f6f6f6;
    }
}

html[class='dark'] .search:hover {
    background-color: #242424;
}
</style>
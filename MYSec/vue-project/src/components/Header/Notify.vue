<template>
    <div class="notify">
        <el-badge is-dot>
            <el-icon>
                <Bell />
            </el-icon>
        </el-badge>
    </div>
</template>

<script setup>



</script>

<style>
.notify {
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

html[class='dark'] .notify:hover {
    background-color: #242424;
}
</style>
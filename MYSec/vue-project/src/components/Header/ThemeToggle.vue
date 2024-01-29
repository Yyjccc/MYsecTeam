<template>
    <div class="themeToggle">
        <el-tooltip effect="dark" content="主题切换" placement="bottom">
            <el-icon @click="changeTheme">
                <component :is="isDark?'Sunny':'MoonNight'"></component>
            </el-icon>

        </el-tooltip>
    </div>
</template>

<script setup>
import { useDark, useToggle } from '@vueuse/core'

const isDark = useDark({
    selector: 'html',
    attribute: 'class',
    valueDark: 'dark',
    valueLight: 'light',
});


function changeTheme() {
    const toggleDark = useToggle(isDark);
    toggleDark();
}


</script>

<style>
.themeToggle {
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

html[class='dark'] .themeToggle:hover {
    background-color: #242424;
}
</style>
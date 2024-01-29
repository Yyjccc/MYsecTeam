<template>
    <div class="userDropDown">
        <el-dropdown trigger="click" @command="handleCommand">
            <span class="el-dropdown-link">
                {{ user.username }}
                <el-icon>
                    <arrow-down />
                </el-icon>
            </span>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item icon="User" command="userInfo">个人信息</el-dropdown-item>
                    <el-dropdown-item icon="SwitchButton" command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
    </div>
</template>
<script setup>
import { computed } from "vue"
import cookie from "../../common/cookie.js"
import { useRouter } from 'vue-router'
const router = useRouter();
function handleCommand(val) {
    console.log(val)
    if (val === 'logout') {
        cookie.remove("jwt")
        cookie.remove("JSESSIONID")
        router.push('/login')
    } else if (val === 'userInfo') {

    }

}

const user = computed(() => {
    return cookie.getUserByJwt();
})


</script>

<style>
.userDropDown {
    cursor: pointer;
    width: 80px;
    height: 100%;
    padding: 0 10px;
    box-sizing: border-box;
    display: flex;
    justify-content: center;
    align-items: center;

    &:hover {
        background-color: #f6f6f6;
    }

    .el-dropdown-link {
        display: flex;
        align-items: center;
    }
}

html[class='dark'] .userDropDown:hover {
    background-color: #242424;
}
</style>
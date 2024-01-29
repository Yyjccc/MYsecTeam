<template>
    <el-row class="login-container">
        <el-col :lg="16" :md="12" class="left">
            <div>
                <div class="font-bold text-5xl text-light-50 mb-4 ">明月安全团队</div>
                <div class="text-gray-200 text-sm">这是MYSec团队训练交流比赛一体化平台</div>
            </div>
        </el-col>
        <el-col :lg="8" :md="12" class="right">
            <h2 class="font-bold text-3xl text-gray-800">欢迎回来</h2>
            <div class="flex items-center justify-center my-5 text-gray-500 space-x-2">
                <span class="h-[1px] w-16 bg-gray-200"></span>
                <span>账号密码登录</span>
                <span class="h-[1px] w-16 bg-gray-200"></span>
            </div>
            <el-form ref="formRef" :rules="rules" :model="form" class="w-[250px]">
                <el-form-item prop="username">
                    <el-input v-model="form.username" placeholder="请输入用户名">
                        <template #prefix>
                            <el-icon>
                                <user />
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="form.password" type="password" show-password placeholder="请输入密码">
                        <template #prefix>
                            <el-icon class="el-input__icon">
                                <lock />
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button round color="#626aef" class="w-[250px]" type="primary" @click="onSubmit">登 录</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<script setup>

import { ref, reactive } from 'vue'
import util from "../common/utils"
import { useRouter } from 'vue-router'
import userAPI from '../api/user'
import cookie from '../common/cookie'


const router = useRouter();
// do not use same name with ref
const form = reactive({
    username: '',
    password: '',
})
//表单校验
const rules = {
    username: [
        {
            required: true, message: '用户名不能为空', tigger: 'blur'
        },
    ],
    password: [{
        required: true, message: '用户名不能为空', tigger: 'blur'
    },],
}
const formRef = ref(null)
const onSubmit = () => {
    formRef.value.validate((vaild) => {
        if (!vaild) {
            return false
        }
        userAPI.login(form.username, form.password).then(
            (res) => {
                if (res.data.ok) {
                    //提示成功
                    util.Message("登录成功")
                    //存储token
                    if (res.data.info != null) {
                        cookie.setJwt(res.data.info.jwt)
                        cookie.set("JSESSIONID", res.data.info.JSESSIONID)

                    }
                    //跳转页面
                    router.push('/')
                } else {
                    util.Message(res.data.msg || '请求失败', 'error')
                }
            }
        )
    })
}
</script>

<style>
.login-container {
    @apply min-h-screen bg-indigo-500;
}

.login-container .left {
    @apply flex items-center justify-center
}

.login-container .right {
    @apply bg-light-50 flex items-center justify-center flex-col
}
</style>
<template>
    <div class="avator">
        <el-avatar :size="30" icon="UserFilled" :src="url" />
    </div>
</template>

<script setup>
import { computed } from 'vue'
import cookie from '../../common/cookie';
import api from '../../api/user';
import axios from '../../axios';

const url = computed(() => {
    const user = cookie.getUserByJwt()
    if (user.avatar) {

        api.getAvastor(user.id).then((res) => {
            if (res.status == 404) {
                return null;
            } else {
                return axios.defaults.baseURL + '/upload/user/' + id + '/' + id + '.png'
            }
        })
    } else {
        return null;
    }

})


</script>

<style>
.avator {
    display: flex;
}
</style>
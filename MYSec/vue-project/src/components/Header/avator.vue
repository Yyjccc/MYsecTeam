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
    const id = cookie.getUserByJwt().id;
    api.getAvastor(id).then((res) => {
        if (res.status == 404) {
            return null;
        } else {
            return axios.defaults.baseURL + '/upload/user/' + id + '/' + id + '.png'
        }
    })

})


</script>

<style>
.avator {
    display: flex;
}
</style>
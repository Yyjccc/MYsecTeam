<template>
    <el-scrollbar>
        <div class="scrollbar-flex-content">
            <el-tag v-for="(item, index) in tags" :key="item" :effect="activePath === item.path ? 'dark' : 'plain'"
                style="margin-right: 5px;cursor: pointer;" :closable="!!(tags.length - 1)" @click="goPage(item.path)"
                @close="handleClose(index)">
                {{ item.name }}
            </el-tag>
        </div>
    </el-scrollbar>
</template>
<script setup>
import { reactive, watch, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import common from '../../common/common';

const activePath = ref('')
const route = useRoute();
const router = useRouter();
const tags = reactive([])

watch(
    () => route,
    (to) => {
        activePath.value = to.path
        const hasTag = tags.find((item) => item.path === to.path)
        const t = common.findName(to.path).pop();
        if (!hasTag) {
            tags.push({
                name: t,
                path: to.path
            });
        }
    },
    {
        deep: true,
    });

function goPage(path) {
    router.push(path)
}

function handleClose(idx) {
    if (idx == 0) {
        router.push(tags[idx + 1].path)
    } else {
        router.push(tags[idx - 1].path)
    }
    tags.splice(idx, 1);
}
</script>

<style>
.scrollbar-flex-content {
    display: flex;
}
</style>
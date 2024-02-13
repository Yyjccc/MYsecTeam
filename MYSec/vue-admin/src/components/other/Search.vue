<template>
    <div id="search">
        <el-form :model="search">
            <el-form-item :label-width="formLabelWidth" style="margin-top: 8px;">
                <template #label>
                    <span style="font-size: 22px;margin-top: 8px;">搜索</span>
                </template>
                <el-select v-model="search.type" class="m-2" placeholder="请选择搜索的字段" size="large">
                    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
            </el-form-item>
            <div id="searchinput">
                <el-input v-model="search.value" style=" width: 260px;
    height: 40px;" autocomplete="off" placeholder="输入搜索值" />
                <el-button v-if="visualReturn" @click="ReturnAll" type="success" style="
    position: relative;left: 150px; height: 40px;width: 75px;">返回</el-button>
            </div>
        </el-form>
        <el-button type="primary" id="serbutton" @click="searchActon(search)">搜索</el-button>
    </div>
    <el-button type="info" id="add" @click="toaddform">添加</el-button>
    <div id="tabletitle" style="display: flex;float: left;">
        <span>{{ mytypes + searchMode }}</span>
    </div>
</template>
<script>
import { ref, reactive, } from 'vue'


export default {
    props: {
        options: {
            type: Array,
            required: true,
        },
        mytypes: {
            type: String,
            required: true
        }
    },
    setup(props, { emit }) {

        const search = reactive({
            type: '',
            value: '',
        });
        const searchMode = ref("全部数据");
        const formLabelWidth = '140px';
        const visualReturn = ref(false);


        const searchActon = (search) => {
            searchMode.value = "搜索结果";
            visualReturn.value = true;
            emit('search-action', { type: search.type, value: search.value });
        }

        const ReturnAll = () => {
            searchMode.value = "全部数据";
            visualReturn.value = false;
            emit('return-all', '');
        }

        const toaddform = () => {
            emit('to-addform', '');
        }
        return {
            search, formLabelWidth, visualReturn, searchMode,
            toaddform, searchActon, ReturnAll

        }
    }
}

</script>

<style>
#searchinput {
    position: relative;
    left: 450px;
    top: -58px;

}

input {
    width: 100%;
    /* 使输入框宽度等于父元素的宽度 */
    box-sizing: border-box;
    /* 让宽度包括 border 和 padding */
    padding: 5px;
    /* 适当的 padding，使输入框不紧贴边框 */
}

.el-button--text {
    margin-right: 15px;
}

.el-select {
    width: 300px;
}

.el-input {
    width: 300px;
}

.dialog-footer button:first-child {
    margin-right: 10px;
}

.custom-table-container {
    margin-top: -85px;



}

.custom-table {
    width: 100%;

}

#add {
    position: relative;
    left: 1000px;
    top: -139px;
    width: 65px;
    height: 40px;
}

#serbutton {
    position: relative;
    left: 720px;
    top: -98px;
    height: 40px;
    width: 75px;
}

#tabletitle {
    font-size: 24px;
    position: absolute;
    float: left;
    left: 460px;
    top: 65px;
}
</style>
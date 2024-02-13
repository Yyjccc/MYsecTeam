<template>
    <div>
        题目管理
    </div>

    <div class="data">
        <div class="search">
            <Search />
        </div>
        <div class="mainTable">
            <el-table :data="tableData" style="width: 100%" :default-sort="{ prop: 'date', order: 'descending' }">
                <el-table-column fixed prop="index" label="序号" width="100" />
                <el-table-column prop="id" label="id" width="200" />
                <el-table-column prop="name" label="题目名字" width="120" />
                <el-table-column prop="class" label="分类" width="120" />
                <el-table-column prop="solved" label="已解出" width="120" />
                <el-table-column prop="wp" label="wp数量" width="100" />
                <el-table-column prop="Date" label="上传日期" width="120" />
                <el-table-column fixed="right" label="选项" width="120">
                    <template #default="{ row }">
                        <el-button link type="primary" size="small" @click="handledetail(row)">详情</el-button>
                        <el-button link type="primary" size="small" @click="startEdit(row)">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="pagination">
            <el-pagination background layout="prev, pager, next" :total="alldata.length" />
        </div>

    </div>

    <el-dialog v-model="dialogTableVisible" title="题目详情" width="800">
        <div v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
            <el-descriptions :title="'id: ' + detailsData.id + ''" :column="2" border>

                <el-descriptions-item label="题目名称" label-align="center" align="center">{{ detailsData.title
                }}</el-descriptions-item>
                <el-descriptions-item label="题目描述" label-align="center" align="center">{{ detailsData.description
                }}</el-descriptions-item>

                <el-descriptions-item label="分类" label-align="center" align="center"> <el-tag size="small">{{
                    detailsData.class
                }}</el-tag></el-descriptions-item>
                <el-descriptions-item label="标签" label-align="center" align="center"> Province</el-descriptions-item>
                <el-descriptions-item label="提示" label-align="center" align="center">提示</el-descriptions-item>
                <el-descriptions-item label="附件" label-align="center" align="center">附件</el-descriptions-item>
                <el-descriptions-item label="容器" label-align="center" align="center">{{ detailsData.docker
                }}</el-descriptions-item>
                <el-descriptions-item label="难度等级" label-align="center" align="center">{{ detailsData.level
                }}</el-descriptions-item>
                <el-descriptions-item label="评分" label-align="center" align="center">{{ detailsData.rank
                }}</el-descriptions-item>
                <el-descriptions-item label="上传日期" label-align="center" align="center">{{ detailsData.date
                }}</el-descriptions-item>
                <el-descriptions-item label="来源" label-align="center" align="center">{{ detailsData.source
                }}</el-descriptions-item>
                <el-descriptions-item label="提供者" label-align="center" align="center">{{ detailsData.provider
                }}</el-descriptions-item>
            </el-descriptions>



            <div id="t1">

                <el-collapse @change="handleChange">
                    <el-collapse-item title="WriteUp" name="1">
                        <div>
                            <el-table :data="wp" stripe style="width: 100%" border>
                                <el-table-column prop="index" label="序号" width="100" />
                                <el-table-column prop="link" label="链接" width="180" />
                                <el-table-column prop="owner" label="作者" width="150" />
                                <el-table-column prop="date" label="日期" width="120" />
                            </el-table>
                        </div>
                    </el-collapse-item>
                    <el-collapse-item title="已解出" name="2">
                        <div>
                            <el-table :data="solved" stripe style="width: 100%" border>
                                <el-table-column prop="index" label="序号" width="100" />
                                <el-table-column prop="user" label="用户" width="150" />
                                <el-table-column prop="date" label="日期" width="120" />
                            </el-table>
                        </div>
                    </el-collapse-item>
                </el-collapse>

            </div>
        </div>
    </el-dialog>


    <el-dialog ref="ruleFormRef" v-model="editVisible" title="编辑题目" width="800">
        <div v-infinite-scroll="load" class="infinite-list" style="overflow: auto">

            <el-form :model="form" label-width="120px">
                <el-form-item label="Activity name">
                    <el-input v-model="form.name" />
                </el-form-item>
                <el-form-item label="Activity zone">
                    <el-select v-model="form.region" placeholder="please select your zone">
                        <el-option label="Zone one" value="shanghai" />
                        <el-option label="Zone two" value="beijing" />
                    </el-select>
                </el-form-item>
                <el-form-item label="Activity time">
                    <el-col :span="11">
                        <el-date-picker v-model="form.date1" type="date" placeholder="Pick a date" style="width: 100%" />
                    </el-col>
                    <el-col :span="2" class="text-center">
                        <span class="text-gray-500">-</span>
                    </el-col>
                    <el-col :span="11">
                        <el-time-picker v-model="form.date2" placeholder="Pick a time" style="width: 100%" />
                    </el-col>
                </el-form-item>
                <el-form-item label="Instant delivery">
                    <el-switch v-model="form.delivery" />
                </el-form-item>
                <el-form-item label="Activity type">
                    <el-checkbox-group v-model="form.type">
                        <el-checkbox label="Online activities" name="type" />
                        <el-checkbox label="Promotion activities" name="type" />
                        <el-checkbox label="Offline activities" name="type" />
                        <el-checkbox label="Simple brand exposure" name="type" />
                    </el-checkbox-group>
                </el-form-item>
                <el-form-item label="Resources">
                    <el-radio-group v-model="form.resource">
                        <el-radio label="Sponsor" />
                        <el-radio label="Venue" />
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="Activity form">
                    <el-input v-model="form.desc" type="textarea" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">Create</el-button>
                    <el-button>Cancel</el-button>
                </el-form-item>
            </el-form>
        </div>
    </el-dialog>
</template>

<script setup>
import { onMounted, ref, reactive } from "vue"
import Search from "../../../components/other/Search.vue"
import api from "../../../api/admin/targets"


const dialogTableVisible = ref(false)
const editVisible = ref(false)

const tableData = ref([])

const detailsData = ref({
    id: 0,
    title: "",
    wp: [],
    solved: [],
    date: "",
    class: "web",
    label: "",
    source: "",
    provider: "",
    description: "",
    hints: [],
    attachment: "",
    docker: false,
    level: 0,
    rank: 0
})

const form = reactive({
    name: '',
    region: '',
    date1: '',
    date2: '',
    delivery: false,
    type: [],
    resource: '',
    desc: '',
})

const alldata = ref([])
const wp = ref([{
    index: 0,
    link: "",
    owner: "",
    date: ""
}])
const solved = ref([{
    index: 0,
    user: "",
    date: ""
}])
onMounted(async () => {
    await api.getAll().then((res) => {
        if (res.status == 200) {
            const resData = res.data.info
            alldata.value = resData.map((item, index) => ({
                index: index + 1, // You can adjust the index starting point if needed
                ...item,
            }));
            console.log(alldata.value)
            const t = api.convertArr(alldata.value)
            // api.convert(res.data.info[1])
            tableData.value = t
        } else {

        }
    })
});

const load = () => {

}
function handleData(data) {
    return {
        id: data.id,
        title: data.name,
        wp: data.wp,
        solved: [],
        date: data.date,
        class: data.category,
        label: data.labels,
        source: data.source,
        provider: data.provider,
        description: data.description,
        hints: data.hints,
        attachment: data.attachment,
        docker: false,
        level: data.level,
        rank: data.rank
    }
}


function handledetail(row) {
    detailsData.value = handleData(alldata.value[row.index - 1])
    console.log(detailsData.value)
    dialogTableVisible.value = true
}


function startEdit(row) {
    editVisible.value = true

}

</script>
<style scoped>
.data {
    position: relative;

    top: 100px;
    /* display: flex; */
    margin: 20px;
}

.pagination {
    position: absolute;
    bottom: -80px;

    left: 28%;
}

:deep(.my-label) {
    background: var(--el-color-success-light-9) !important;
}

:deep(.my-content) {
    background: var(--el-color-danger-light-9);
}

.t1 {
    margin-top: 50px;
    width: 100%;
    overflow: hidden;
}

.infinite-list {
    height: 500px;
    padding: 0;
    margin: 0;
    list-style: none;
}

.infinite-list .infinite-list-item {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100px;
    background: var(--el-color-primary-light-9);
    margin: 10px;
    color: var(--el-color-primary);
}

.infinite-list .infinite-list-item+.list-item {
    margin-top: 10px;
}


#t1 {
    margin-top: 50px;
}
</style>


<template>
    <el-menu active-text-color="#ffd04b" background-color="#2d3a4b" default-active="/" text-color="#fff" router
        :collapse="!$store.state.layoutStore.isExpand" class="items-center" style="height: 100%;" :unique-opened="true">

        <MenuItem v-for="item in menuList" :key="item.id" :data="item" />

    </el-menu>
</template>
<script setup>
import MenuItem from './MenuItem.vue';

//菜单数据
const menuList = [{
    id: 100,
    url: '/',
    icon: 'HomeFilled',
    title: '首页',
},
{
    id: 10,
    url: '/1',
    title: '内容管理',
    children: [{
        id: 11,
        url: 'topic',
        title: '题目管理',
        icon: 'dish'
    },
    {
        id: 12,
        url: 'article',
        title: '文章管理',
        icon: 'documentCopy'
    },
    {
        id: 13,
        url: 'task',
        title: '任务管理',
        icon: 'list'
    },
    {
        id: 14,
        url: 'post',
        title: '帖子管理',
        icon: 'Cherry'
    }
    ]
},
{
    id: 20,
    url: '/2',
    title: "用户管理",
    children: [
        {
            id: 21,
            title: '权限管理',
            icon: 'management',
            url: 'authorityManage'
        },

        {
            id: 22,
            url: 'personalData',
            icon: 'userFilled',
            title: '个人数据',

        }]
},
{
    id: 30,
    url: '/3',
    title: '比赛管理',
    children: [{
        id: 31,
        url: 'holdCompetition',
        title: '发布比赛',
        icon: 'flag'
    },
    {
        id: 32,
        url: 'historyData',
        title: '历史数据',
        icon: 'MostlyCloudy'
    }]
},
{
    id: 40,
    url: '/4',
    title: '展示设置',
    children: [
        {
            id: 41,
            url: 'homeDisplay',
            title: '首页展示',
            icon: 'PriceTag'
        },
        {
            id: 42,
            url: 'shootingRangeDisplay',
            title: '靶场数据',
            icon: 'coin'
        }
    ]
},
{
    id: 50,
    url: '/5',
    title: '内容发布',
    children: [
        {
            id: 51,
            url: 'postTopic',
            icon: 'folderAdd',
            title: '发布题目'
        },
        {
            id: 52,
            url: 'postArticle',
            icon: 'document',
            title: '发布文章'
        },
        {
            id: 53,
            url: 'postTask',
            icon: 'infoFilled',
            title: '发布任务'
        },

        {
            id: 53,
            url: 'announce',
            icon: 'BellFilled',
            title: '发布公告'
        },
    ]
},
{
    id: 60,
    url: '/6',
    title: "系统设置",
    children: [
        {
            id: 61,
            url: 'api',
            icon: 'key',
            title: 'api管理'
        },
        {
            id: 62,
            url: 'server',
            title: '服务器设置',
            icon: 'monitor'
        }
    ],
}
]



</script>

<style>
.el-menu {
    border: none;
}
</style>
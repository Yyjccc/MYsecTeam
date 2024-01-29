import {
    createRouter,
    createWebHashHistory
} from 'vue-router'

import Layout from '../layout/index.vue'
import About from '../pages/about.vue'
import NotFound from '../pages/404.vue'
import Login from '../pages/login.vue'
import server from '../pages/admin/setting/ServiceSetting.vue'
import api from '../pages/admin/setting/api.vue'
import postTopic from '../pages/admin/post/postTopic.vue'
import postArticle from '../pages/admin/post/postArticle.vue'
import postTask from '../pages/admin/post/postTask.vue'
import Announce from '../pages/admin/post/Announce.vue'
import HomeDisplay from '../pages/admin/show/HomeDisplay.vue'
import ShootingRangeDisplay from '../pages/admin/show/ShootingRangeDisplay.vue'
import HoldCompetition from '../pages/admin/contest/HoldCompetition.vue'
import HistoryData from '../pages/admin/contest/HistoryData.vue'
import AuthorityManage from '../pages/admin/user/AuthorityManage.vue'
import PersonalData from '../pages/admin/user/PersonalData.vue'
import Topic from '../pages/admin/content/Topic.vue'
import Article from '../pages/admin/content/Article.vue'
import Post from '../pages/admin/content/Post.vue'
import Task from '../pages/admin/content/Task.vue'
import AdminIndex from '../pages/admin/index.vue'



const routes = [
    {
        path: '/',
        name: 'layout',
        component: Layout,
        children: [
            {
                path: '/',
                component: AdminIndex
            },
            {
                path: 'server',
                component: server
            },
            {
                path: 'api',
                component: api
            },
            {
                path: 'postTopic',
                component: postTopic
            },
            {
                path: 'postArticle',
                component: postArticle
            },
            {
                path: 'postTask',
                component: postTask
            },
            {
                path: 'announce',
                component: Announce
            },
            {
                path: 'homeDisplay',
                component: HomeDisplay
            },
            {
                path: 'shootingRangeDisplay',
                component: ShootingRangeDisplay
            },
            {
                path: 'holdCompetition',
                component: HoldCompetition
            },
            {
                path: 'historyData',
                component: HistoryData
            },
            {
                path: 'authorityManage',
                component: AuthorityManage
            },
            {
                path: 'personalData',
                component: PersonalData
            },
            {
                path: 'topic',
                component: Topic
            },
            {
                path: 'article',
                component: Article
            },
            {
                path: 'post',
                component: Post
            },
            {
                path: 'task',
                component: Task
            }

        ],
    },
    {
        path: '/about',
        component: About
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: NotFound
    },
    {
        path: '/login',
        component: Login
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

export default router;

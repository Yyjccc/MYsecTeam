import router from './index.js'
import cookie from '../common/cookie.js'
import utils from '../common/utils.js'
import useNProgress from '../common/hooks/NProgress.js'

const NProgress = useNProgress();

//全局前置守卫
router.beforeEach((to, from, next) => {
    //开启进度条
    NProgress.start();

    //检查是否登录
    const jwt = cookie.get('jwt')
    if (!jwt && to.path != '/login') {
        utils.Message("请先登录", "error")
        return next({ path: '/login' })
    }
    //防止重复登录
    if (jwt && to.path == '/login') {
        utils.Message("请勿重复登录", "error")
        return next({ path: from.path ? from.path : '/' })
    }
    next();
})


router.afterEach(() => {
    //关闭进度条
    NProgress.done();
})

import store from './store/index.js'
const menuList = store.state.menuStore.menuList

export default {
    //根据路由查找所在的名字
    findName(path) {
        if (path === '/') {
            return ['首页']
        } else {
            let p = path.replace(/^\/+/, '')
            for (let i = 0; i < menuList.length; i++) {
                let o = menuList[i]
                if (o.url === p) {
                    return [o.title];
                }
                if (o.children) {
                    for (let j = 0; j < o.children.length; j++) {
                        if (o.children[j].url === p) {
                            return [o.title, o.children[j].title]
                        }
                    }
                }
            }
        }
    }
}
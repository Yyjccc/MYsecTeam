import { ElNotification } from 'element-plus'


export default {
    //消息提示
    Message(message, type = 'success', duration = 3000, dangerouslyUseHTMLString = false) {
        ElNotification({
            message,
            type,
            dangerouslyUseHTMLString,
            duration
        })
    }
}
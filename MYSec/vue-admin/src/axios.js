import axios from "axios";
import { ElNotification } from 'element-plus'


const service = axios.create({
    baseURL: 'http://127.0.0.1:12345',
})


//添加请求拦截器
service.interceptors.request.use(function (config) {


    return config;
}, function (err) {

    return Promise.reject(err);
});

service.interceptors.response.use(function (response) {

    return response;
}, function (err) {
    ElNotification({
        message: '请检查网络配置' + err.message,
        type: 'error',
        duration: 3000
    })
    return Promise.reject(err);
});



export default service
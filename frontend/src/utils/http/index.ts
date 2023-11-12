import axios from 'axios'
import { ElLoading, ElMessage } from 'element-plus'

const http = axios.create({
    withCredentials: true,
    timeout: 1000,
    baseURL: '/api'
})


let loadinghttp: any = null
// 配置请求拦截器
http.interceptors.request.use(
    config => {
        // 展示 loading 效果
        loadinghttp = ElLoading.service({ fullscreen: true, text: '请求服务器中。。。', background: 'rgba(122, 122, 122, 0.8)' })
        // 配置 Token 认证
        config.headers.Authorization = sessionStorage.getItem('Authorization') ? sessionStorage.getItem('Authorization') : ''
        // console.log(config)
        // 这是固定写法
        return config
    },
    error => {
        // 对请求错误做些什么
        console.log('request error!')
        return Promise.reject(error);
    })


const errorHandle = (status: number) => {
    switch (status) {
        case 400:
            ElMessage.error('信息校验失败')
            break
        case 401:
            ElMessage.error('认证失败')
            break
        case 403:
            ElMessage.error('token校验失败')
            break
        case 404:
            ElMessage.error('请求的资源不存在')
            break
        default:
            break
    }
}

const successHandle = (res: any) => {
    switch (res.code) {
        case 200:
            if (res.msg != '请求成功')
                ElMessage.success(res.msg)
            break
        case 400:
            ElMessage.error(res.msg)
            break
        case 500:
            ElMessage.error(res.msg)
            break
        default:
            break
    }
}

// 配置响应拦截器
http.interceptors.response.use(
    response => {
        // 关闭 loading 效果
        loadinghttp.close()
        successHandle(response.data)
        return response
    },
    error => {
        // 对响应错误做点什么
        loadinghttp.close()
        console.log(error)
        const { response } = error
        if (response) {
            errorHandle(response.status)
            return Promise.reject(response)
        }
        return true
    })

export default http
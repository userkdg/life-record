import axios from 'axios'
import AppConst from '@/config'
import {token_key} from "../api/constantsGlobal";
import {message} from 'ant-design-vue';
import router from "../router/index";

const createBaseRequest = ({ baseURL, otherParams = {} }) => {
  const req = axios.create({
    baseURL,
    otherParams,
    timeout: 15000,
    headers: {
      'Content-Type': 'application/json'
    },
    withCredentials: true,
  })
  // Add a request interceptor
  req.interceptors.request.use(
    (config = {}) => {
      let token = localStorage.getItem(token_key);
      if (token){
        // config.headers.common['token'] = token;
        config.headers[token_key] = token;
      }else {
        router.push('/login')
      }
      return config
    },
    (error) => Promise.reject(error)
  )

  // Add a response interceptor
  req.interceptors.response.use(
    (res) => {
      return res
    },
    (err) => {
      if (err && err.response && err.response.data) {
        if (err.response.data.code === 403 || err.response.data.code === 401) {
          message.error(`${err.response.data.msg}`)
        }
        if (err.response.data.code === 403){
          // 跳到登录
          router.push('/login')
          return ;
        }
      }
      return Promise.reject(err)
    }
  )
  return req
}

export const http = createBaseRequest({
  baseURL: AppConst.APP_BACKEND

})

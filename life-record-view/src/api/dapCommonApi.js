import {http} from '@/services/baseRequest'
import SnowflakeId from "snowflake-id";

/*
    axios.get()
         .then((res)=>{})
         .catch((e)=>{})
         针对系统分类管理的
 */
const baseUri = "/dapSystemCategory";

export const initTopics = ({params} = {}) => {
    if (!params)params = {level: 2}
    if (!params.level || params.level < 1) params.level = 2
    return http
        .get(`/dapSystemCategory/topics/${params.level}`)
        .then(res => {
            let items = res.data.content ? res.data.content : [];
            // items.unshift({id: '#all', codeName: '所有主题'})
            console.log(items)
            return items;
        });

}

/**
 * 下载
 *
 * @param url
 */
export const downloadFile = (url) => {
  const iframe = document.createElement("iframe");
  iframe.style.display = "none";  // 防止影响页面
  iframe.style.height = 0;  // 防止影响页面
  iframe.src = url;
  document.body.appendChild(iframe);  // 这一行必须，iframe挂在到dom树上才会发请求
  // 5分钟之后删除（onload方法对于下载链接不起作用，就先抠脚一下吧）
  setTimeout(()=>{
    iframe.remove();
  }, 5 * 60 * 1000);
}

//用于生成uuid
function S4() {
    return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}

// Initialize snowflake
const snowflake = new SnowflakeId({
    mid: 42,
    offset: (2019 - 1970) * 31536000 * 1000
});

/**
 * js雪花id
 * @returns {null|string}
 */
export const snowflakeId = function () {
    return snowflake.generate();
}

/**
 * js uuid
 * @returns {string}
 */
export const guid = function guid() {
    return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}

export const get = ({id} = {}) => {
    return http.get(baseUri + '/' + id)
}

export const del = ({id} = {}) => {
    return http.del(baseUri + '/' + id)
}

export const update = ({id} = {}) => {
    return http.put(baseUri + '/' + id)
}
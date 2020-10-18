import { http } from '@/services/baseRequest'

export const getActivityList = ({ data, params } = {}) => {
  return http.post('/activity/getActivityList', data, { params })
}

// 获得标签列表
export const getTagsList = ({ data, params } = {}) => {
  return http.post('/dapSystem/tags/pageList', data, { params })
      .then(response => {return response.data})
}

// 删除标签
export const deleteTags = ({ ids } = {}) => {
  return http.delete('/dapSystem/tags/delete', {data:ids})
      .then(response => {return response.data})

}

// 新增标签(批量)
export const insertTags = ([ tagList ] = []) => {
  return http.post('/dapSystem/tags/insert', {tagList:tagList})
      .then(response => {return response.data})

}
/*
export const getOptList = params => { return http.get(`/dapSystem/tags/pageList`, { params: params }); };*/
/*
export const getTags = ({ data, params } = {}) => {
  return http.post('/dapSystem/tags/pageList', data, { params })
}*/

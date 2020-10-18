import { http } from '@/services/baseRequest'

export const getModelPageList = (data) => {
  return http.post('/dapModel/model/pageList', data)
}
export const getTagsPageList = (params) => {
  return http.post('/dapSystem/tags/pageList', {}, { params })
}
export const modelLookup = (params) => {
  return http.get('/dapModel/model/lookup', { params })
}
export const getSysInfoList = (params) => {
  return http.get('/dapSystem/datasource/category/type', { params })
}
export const modifyModelInfo = (data) => {
  return http.post('/dapModel/model/modify', data)
}
export const addModelInfo = (data) => {
  return http.post('/dapModel/model/insert', data)
}
export const deleteModel = (params) => {
  return http.delete('/dapModel/model/delete', { params })
}
export const getUnUseDataSource = (params) => {
  return http.get('/dapSystem/datasource/unUse', { params })
}
export const getModelTablelist = (params) => {
  return http.get('/dapModel/modelTable/list', { params })
}
export const saveModelTable = (data) => {
  return http.post('/dapModel/modelTable/insert', data)
}
export const getTopicOrCategory = (type) => {
  return http.get('/dapSystemCategory/topicOrCategory/' + type)
}
export const addFieldInfo = (data) => {
  return http.post('/dapModel/modelTable/field/insert', data)
}
export const addIndexInfo = (data) => {
  return http.post('/dapModel/modelTable/key/insert', data)
}
export const deleteModelTable = (data) => {
  return http.delete('/dapModel/modelTable/delete', { data })
}
export const getFieldList = (params) => {
  return http.get('/dapStandardDataType/field/list', { params })
}
export const getModelTableInfo = (params) => {
  return http.get('/dapModel/modelTable/info', { params })
}
export const getTableFieldInfo = (params) => {
  return http.post('/dapModel/modelTable/field/detail', {}, { params })
}
export const getTableIndexInfo = (params) => {
  return http.get('/dapModel/modelTable/key/detail', { params })
}
export const addPartitionInfo = (data) => {
  return http.post('/dapModel/modelTable/partition/insert', data)
}
export const getTablePartitionInfo = (params) => {
  return http.get('/dapModel/modelTable/partition/list', { params })
}
export const sqlScan = (data) => {
  return http.post('/dapModel/modelTable/sql/scan', data)
}

export const mulSqlScan = (data) => {
  return http.post('/dapModel/modelTable/sql/export', data)
}

export const getDapStandardBasicList = (data) => {
  return http.post('/dapStandardBasic/list', data)
}

export const getRelationStandardList = (data) => {
  return http.post('/dapModel/modelTable/relation/standard', data)
}

export const getUnMatchStandardList = (data) => {
  return http.post('/dapModel/modelTable/unMatch/relation', data)
}

export const getAlreadyStandardList = (data) => {
  return http.post('/dapModel/modelTable/already/relation', data)
}

export const getUnRelationStandardList = (data) => {
  return http.post('/dapModel/modelTable/unRelation', data)
}

export const deleteTableField = (data) => {
  return http.delete('/dapModel/modelTable/field/delete', {data})
} 

export const deleteTableKey = (data) => {
  return http.delete('/dapModel/modelTable/key/delete', {data})
}

export const deleteTablePartition = (data) => {
  return http.delete('/dapModel/modelTable/partition/delete', {data})
}

export const getNameRecommendList = (data) => {
  return http.post('/dapModel/modelTable/field/nameRecommend', data)
}

export const downLoadSql = (data) => {
  return http.post('/dapModel/modelTable/sql/download', data, { responseType: 'blob'})
}

export const getModelReleaseList = (data) => {
  return http.post('/dapDataModelRecord/release/list', data)
}
export const getModelVerifyList = (data) => {
  return http.post('/dapDataModelRecord/verify/list', data)
}

export const getModelTableReleaseList = (data) => {
  return http.post('/dapDataModelRecord/release/modelTable', data)
}

export const getModifyTableRelease = (params) => {
  return http.get('/dapDataModelRecord/modify/modelTable', {params})
}

export const cancelModelRelease = (params) => {
  return http.post('/dapDataModelRecord/release/cancel', {}, {params})
}

export const applyReleaseRecord = (params) => {
  return http.post('/dapDataModelRecord/model/release', {}, {
    params
  })
}

export const passReleaseRecord = (params) => {
  return http.post('/dapDataModelRecord/release/pass', {}, {
    params
  })
}

export const unPassReleaseRecord = (params) => {
  return http.post('/dapDataModelRecord/release/unPass', {}, {
    params
  })
}

export const applyRelease = (params) => {
  return http.post('/dapDataModelRecord/release', {}, {
    params
  })
}

export const ignoreStandard = (data) => {
  return http.post('/dapModel/modelTable/ignore/relation', data)
}

export const quoteStandard = (data) => {
  return http.post('/dapModel/modelTable/use/relation', data)
}


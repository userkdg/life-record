const my_standard_dataTypeArr =  [
    {id:"代码类",code:0,name: "代码类"},
    {id:"标志类",code:1,name: "标志类"},
    {id:"文本类",code:2,name: "文本类"},
    {id:"金额类",code:3,name: "金额类"},
    {id:"比例类",code:4,name: "比例类"},
    {id:"数值类",code:5,name: "数值类"},
    {id:"日期类",code:6,name: "日期类"},
    {id:"日期时间类",code:7,name: "日期时间类"},
    {id:"时间类",code:8,name: "时间类"},
];
const level_list = [{
    label:'ODS',
    value:'ODS'
},{
    label:'DWD',
    value:'DWD'
},{
    label:'DIM',
    value:'DIM'
},{
    label:'DWS',
    value:'DWS'
},{
    label:'ADS',
    value:'ADS'
}]
//系统分类
const category_list = [{
    label:'  业务系统',
    value:'1'
},{
    label:'数仓系统',
    value:'2'
},{
    label:'数据产品',
    value:'3'
},{
    label:'其他',
    value:'4'
}]

//索引类型
const index_type_list = [{
    label:'空',
    value:1
},{
    label:'normal',
    value:2
},{
    label:'unique',
    value:3
},{
    label:'fulltext',
    value:4
},{
    label:'spatial',
    value:5
}]

//索引方法
const index_method_list = [{
    label:'空',
    value:1
},{
    label:'btree',
    value:2
},{
    label:'hash',
    value:3
}]

//分区类型
const partion_type_list = [{
    label:'range',
    value:0
},{
    label:'list',
    value:1
},{
    label:'hash',
    value:3
},{
    label:'key',
    value:3
}]
//申请状态
const apply_status_list = [{
    label:'已发布',
    value:1
},{
    label:'草稿',
    value:2
},{
    label:'待审核',
    value:3
},{
    label:'不通过',
    value:4
}]
//变更类型
const update_type_list = [{
    label:'增加字段',
    value:1
},{
    label:'删除字段',
    value:2
},{
    label:'修改字段',
    value:3
},{
    label:'创建索引',
    value:4
},{
    label:'删除索引',
    value:5
},{
    label:'新增表',
    value:6
},{
    label:'删除表',
    value:7
}]

 //业务线
const business_line_list = [{
    label:'现代渠道',
    value:'1'
},{
    label:'电商',
    value:'2'
},{
    label:'财务',
    value:'3'
},{
    label:'供应链',
    value:'4'
},{
    label:'人事行政后勤',
    value:'5'
},{
    label:'社区（含高校等）',
    value:'6'
},{
    label:'洗衣服务',
    value:'7'
},{
    label:'月亮商城',
    value:'8'
},{
    label:'客服',
    value:'9'
},{
    label:'管理学院',
    value:'10'
}]

const token_key = 'dap-token';
const menus_key = 'dap_menus';
const user_info_key = 'user_info';

export default {
    
    /**
     * 页面跳转的操作类型， 1 新增 2 编辑 3 查看
     */
    PAGE_OPERATION: {
        Add: 1, Edit: 2, View: 3
    },
    dto: {},
    /**
     * DATA_STANDARD_TYPE 标准类型 0 基础标准  1 指标标准
     */
    DATA_STANDARD_TYPE: {
        basic: 0,
        index: 1
    },
    HTTP_API: {

    },
    // 制定依据
    standard_referRulesArr: [
        {id: "COUNTRY_STD", code: 1, name: "国家标准"},
        {id: "PROFESSION_STD", code: 2, name: "行业标准"},
        {id: "COMPANY_STD", code: 3, name: "内部标准"},
        {id: "OTHER_STD", code: 4, name: "其他标准"},
    ],
    /**
     * 数据标准类型常量
     */
    standard_dataTypeArr: my_standard_dataTypeArr,
    standard_dataType_codeType: my_standard_dataTypeArr.filter(d => d.id==='代码类'),
    indexFormatArr: my_standard_dataTypeArr.filter(d => d.id === '金额类' || d.id === '数值类'),
    standard_auditStatus: {
        草稿:"草稿",
        待审批:"待审批",
        不通过:"不通过",
        已发布:"已发布",
        撤销:"撤销",
        通过:"通过",
        新版本:"新版本",
    },
    /**
     * 针对主题和分类组合显示 的工具类
     * @param arr
     * @returns {any}
     */
    formatTopicAndCategory:(arr) => {
        // let arr = [this.viewData.topicName, this.viewData.firstCategoryName,this.viewData.secondCategoryName];
        let arrStr = arr.filter(a => a).join('\\');
        return arrStr.replace('\\\\','\\').endsWith('\\') ? arrStr.substring(0, arrStr.length - 1) : arrStr;
    },
    /**
     * 稽核信息
     */
    auditStatusArr:[
        {id: "AUDIT_WAIT", code: 0, name: "待稽核"},
        {id: "AUDIT_DOING", code: 1, name: "稽核中"},
        {id: "AUDIT_DONE", code: 2, name: "已稽核"}
    ],
    /**
     * 返回前一页
     */
    back() {
        if (window.history.length <= 1) {
            this.$router.push({path: '/'})
        } else {
            this.$router.go(-1)
        }
    },
    /**
     * 获取url token写入localStorage
     */
    localStorageTokenByUrl: (url) => {
        let name = token_key
        let token = decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(url) || [, ""])[1].replace(/\+/g, '%20')) || null
        console.log(`token:${token}`)
        if (token){
            localStorage.setItem(name, token)
        }
    },
    localStorageTokenByToken: (token) => {
        if (token) {
            localStorage.setItem(token_key, token)
        }
    },
    async localStorageMenusByToken(that){
        let token = localStorage.getItem(token_key)
        let resp = await that.$http.post(`/menus?token=${token}`)
        let menuJsonStr = JSON.stringify(resp.data.content)
        console.log(`menus:${menuJsonStr}`)
        localStorage.setItem(menus_key, menuJsonStr)
    },
    /**
     * 用户信息
     * @param that
     */
    async storeUserInfo(that){
        let resp = await that.$http.get(`/userInfo?token=${localStorage.getItem(token_key)}`)
        if (resp && resp.data.code == 200 && resp.data.content.length > 0) {
            console.log(resp.data.content)
            let userInfoStr = resp.data.content[0]
            localStorage.setItem(user_info_key, userInfoStr)
            return userInfoStr;
        }else {
            that.$message.error("获取用户信息异常")
            return {};
        }
    },
    getUserInfo(){
        return localStorage.getItem(user_info_key)
    },
    /**
     * 判断是否存在可访问菜单
     *
     * @param flag
     * @returns {*}
     */
    permissionView(flag = ''){
        let menusStr = localStorage.getItem(menus_key)
        let menusMapJson = JSON.parse(menusStr) || {}
        // eslint-disable-next-line no-prototype-builtins
        let hadAuth = menusMapJson.hasOwnProperty(flag)
        console.log(`${flag} auth => ${hadAuth}`)
        return hadAuth
    }
}
export {
    token_key,
    menus_key,
    category_list,
    business_line_list,
    level_list,
    index_type_list,
    index_method_list,
    partion_type_list,
    apply_status_list,
    update_type_list
}
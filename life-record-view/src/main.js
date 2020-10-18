import './style/common.less'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {default as notification} from "ant-design-vue/lib/notification";
import moment from 'moment'//前端时间处理js
import {http} from "./services/baseRequest";
import GLOBAL from './api/constantsGlobal'//引用文件 用于全局定义变量或函数

import {
  message,
  Button,
  Col,
  Dropdown,
  Row,
  Breadcrumb,
  Cascader,
  Checkbox,
  DatePicker,
  Form,
  FormModel,
  Icon,
  Input,
  InputNumber,
  Layout,
  List,
  Pagination,
  Modal,
  Menu,
  Select,
  Table,
  Tabs,
  Tree,
  Upload,
  Popconfirm,
  Radio,
  Divider,
  Alert,
  Timeline,
  BackTop
} from 'ant-design-vue'

Vue.use(Button)
Vue.use(Col)
Vue.use(Dropdown)
Vue.use(Row)
Vue.use(Breadcrumb)
Vue.use(Cascader)
Vue.use(Checkbox)
Vue.use(DatePicker)
Vue.use(Form)
Vue.use(FormModel)
Vue.use(Icon)
Vue.use(Input)
Vue.use(InputNumber)
Vue.use(Layout)
Vue.use(List)
Vue.use(Pagination)
Vue.use(Modal)
Vue.use(Menu)
Vue.use(Select)
Vue.use(Table)
Vue.use(Tabs)
Vue.use(Tree)
Vue.use(Upload)
Vue.use(Popconfirm)
Vue.use(Radio)
Vue.use(Divider)
Vue.use(Alert)
Vue.use(Timeline)
Vue.use(BackTop)

Vue.prototype.$message = message
Vue.prototype.$notification = notification;
Vue.prototype.$info = Modal.info;
Vue.prototype.$success = Modal.success;
Vue.prototype.$error = Modal.error;
Vue.prototype.$warning = Modal.warning;
Vue.prototype.$confirm = Modal.confirm;
Vue.prototype.$destroyAll = Modal.destroyAll;
moment.locale('zh-cn');//需要汉化
Vue.prototype.$moment = moment;//赋值使用
Vue.prototype.$alert = Alert;

Vue.config.productionTip = false
Vue.prototype.$http = http;
Vue.prototype.bmGlobal=GLOBAL;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

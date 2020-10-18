<template>
  <div class="home">
    <a-layout>
      <a-layout-header>
        <a-row>
          <a-col :span="20">
            <!-- <img width="50" src="@/assets/logo.png" alt="" />{{ VUE_APP_TITLE }} -->
            <a-icon type="database" style="margin-right:10px;"/>{{ VUE_APP_TITLE }}
          </a-col>
          <a-col :span="4">
            <span>{{userName}}</span>
            <a-button style="margin-left:10px;float:right" type="default" @click="logout">退出</a-button>
          </a-col>
        </a-row>

      </a-layout-header>
      <a-layout>
        <a-layout-sider width="200" style="background: #000c17">
          <a-menu
            mode="inline"
            theme="dark"
            @click="handleClick"
          >
            <a-sub-menu key="sub1" @titleClick="titleClick">
              <span slot="title" class="data-standard-title"
                ><a-icon type="mail" /><span>数据标准</span></span
              >
              <a-menu-item key="1" v-if="viewBasicQuery">
                <router-link to="/basicDataStandardQuery">基础标准查询</router-link>
<!--                <router-link to="/basicDataStandardQuery">基础标准查询</router-link>-->
              </a-menu-item>
              <a-menu-item key="2">
                <router-link to="/indexDataStandardQuery">指标标准查询</router-link>
              </a-menu-item>
              <a-menu-item key="3">
                <router-link to="/modifyStandardList">变更列表</router-link>
              </a-menu-item>
              <a-menu-item key="4">
                <router-link to="/auditStandardList">审批列表</router-link>
              </a-menu-item>
              <a-menu-item key="5">
                <router-link to="/systemAuditList">关联系统稽核</router-link>
              </a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub2" @titleClick="titleClick">
              <span slot="title"
                ><a-icon type="appstore" /><span>模型管理</span></span
              >
              <a-menu-item key="6">
                <router-link to="/modelCardView">模型设计</router-link>
              </a-menu-item>
              <a-menu-item key="7">
                <router-link to="/publishedModel">发布模型</router-link>
              </a-menu-item>
              <a-menu-item key="8">
                <router-link to="/verifyModel">审核模型</router-link>
              </a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub3" @titleClick="titleClick">
              <span slot="title"><a-icon type="setting" /><span>系统管理</span></span>
              <a-menu-item key="11">
                <router-link to="/systemTagList">标签管理</router-link>
              </a-menu-item>
               <a-menu-item key="9">
                <router-link to="/systemDatasource">数据源管理</router-link>
              </a-menu-item>
              <a-menu-item key="10">
                <router-link to="/systemConfiguration">配置系统</router-link>
              </a-menu-item>
            </a-sub-menu>
          </a-menu>
        </a-layout-sider>
        <a-layout-content>
          <div v-if="isHome">
            <a-layout >
              <h5 style="font-size: 20px">成长记录本</h5>
              <a-layout-content>
                <a-row>
                  <a-button icon="plus"></a-button>
                </a-row>
                <br/>
                <br/>
                <a-row>
                  <a-timeline>
                    <a-timeline-item>创建服务现场 2020-09-04</a-timeline-item>
                    <a-timeline-item>初步排除网络异常 2020-09-03</a-timeline-item>
                    <a-timeline-item>技术测试异常 2020-09-02</a-timeline-item>
                    <a-timeline-item>网络异常正在修复 2020-09-01</a-timeline-item>
                  </a-timeline>
                </a-row>

                <a-back-top>
                  <div class="ant-back-top-inner">
                    <a-icon type="arrow-up" />
                  </div>
                </a-back-top>

              </a-layout-content>

              <a-layout-footer>来源于梁洁美的记录</a-layout-footer>
            </a-layout>
          </div>
          <keep-alive>
            <router-view v-if="isKeepAlive">
              <!-- 这里是会被缓存的视图组件，比如 page1,page2 -->
            </router-view>
          </keep-alive>
          <router-view v-if="!isKeepAlive">
            <!-- 这里是不被缓存的视图组件，比如 page3 -->
          </router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>
import {token_key} from "../../api/constantsGlobal";

const { VUE_APP_TITLE } = process.env
export default {
  name: 'Home',
  data() {
    return {
      isHome: false,
      current: ['sub1'],
      openKeys: ['sub1'],
      VUE_APP_TITLE,
      viewBasicQuery: this.bmGlobal.permissionView('standard:query:basic:query'),
      userName: this.bmGlobal.getUserInfo()['USERNAME'],
    }
  },
  computed:{
    isKeepAlive(){
      return !(this.$route.meta.keepAlive===false)
    }
  },
  watch: {
    openKeys(val) {
      console.log('openKeys', val)
    }
  },
  async mounted() {
    this.isHome = true
    // 初始化用户
    let userInfo = await this.bmGlobal.storeUserInfo(this)
    this.userName = userInfo.USER_NAME
    // 初始化菜单
    await this.bmGlobal.localStorageMenusByToken(this)
  },
  methods: {
    handleClick(e) {
      console.log('click', e)
    },
    titleClick(e) {
      console.log('titleClick', e)
    },
    logout(){
      localStorage.removeItem(token_key)
      this.$router.push('/login')
    }
  }
}
</script>
<style lang="less" scoped>
.home {
  height: 100%;
  & > .ant-layout {
    height: 100%;
  }
}
.ant-layout-header {
  background: #3e8ef7;
  color: #fff;
  font-size: 20px;
  height:50px;
  line-height: 50px;
  padding: 0 20px;
}
.ant-layout-sider{
  overflow: auto;
}
.ant-layout-content{
    padding:10px;
}
.ant-layout-sider-children{
    overflow: auto;
}
.data-standard-title{
  a{
    color:rgba(255, 255, 255, 0.65);
  }
}
.ant-menu-submenu-open{
  .data-standard-title{
    a{
      color:rgba(255, 255, 255, 1);
    }
  }
}
</style>

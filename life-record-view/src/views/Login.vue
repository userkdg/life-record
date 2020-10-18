<template>
    <a-form-model layout="inline" :model="formInline" @submit="handleSubmit" @submit.native.prevent>
        <a-form-model-item>
            <a-input v-model="formInline.user" placeholder="Username">
                <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)"/>
            </a-input>
        </a-form-model-item>
        <a-form-model-item>
            <a-input v-model="formInline.password" type="password" placeholder="Password">
                <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)"/>
            </a-input>
        </a-form-model-item>
        <a-form-model-item>
            <a-button
                    type="primary"
                    html-type="submit"
                    :disabled="formInline.user === '' || formInline.password === ''"
            >
                登陆
            </a-button>
        </a-form-model-item>
    </a-form-model>
</template>
<script>

  import {token_key} from "../api/constantsGlobal";

  export default {
    data() {
      return {
        formInline: {
          user: '80560490',
          password: 'ebydibt8',
          captcha: '',
        },
      };
    },
    mounted() {
      // 默认为来源蓝数通
      this.main(1)
    },
    methods: {
      async handleSubmit(e) {
        console.log(this.formInline);
        if (e) e.preventDefault();
        await this.main(2)
      },
      /**
       * main
       * @param flag =1 为 (外部系统重定向过来，进入系统） =2 为登陆方式进入
       * @returns {Promise<void>}
       */
      async main(flag = 1) {
        let token = undefined;
        // 首先分析url是否有token，有则验证有效性 (外部系统重定向过来）
        this.bmGlobal.localStorageTokenByUrl(window.location.href)
        let urlToken = localStorage.getItem(token_key)
        if (flag === 1) {
          if (urlToken) {
            let resp = await this.$http.get(`/userInfo?token=${urlToken}`)
            if (resp && resp.data.code == 200 && resp.data.content.length > 0) {
              // 用户信息
              console.log(resp.data.content)
              token = urlToken;
            }
          }
        } else if (flag === 2) {
          // （独立系统登陆）
          let resp = await this.$http.post(`/login?username=${this.formInline.user.trim()}&password=${this.formInline.password}&captcha=${this.formInline.captcha}`).catch(e => e)
          if (resp && resp.data.code == 200) {
            token = resp.data.content.token
          } else {
            console.log(`相关错误信息：${resp.data.msg}`)
            this.$message.error(`登录失败`)
          }
        }
        if (token) {
          this.bmGlobal.localStorageTokenByToken(token)
          this.$message.success(`登录成功`)
          await this.$router.push(`/home?token=${localStorage.getItem(token_key)}`)
        } else {
          await this.$router.push(`/login`)
        }
      }
    },
  }
</script>

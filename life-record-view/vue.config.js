const devWebpackConfig = require('./webpackConfig/webpack.dev.config')
const prodWebpackConfig = require('./webpackConfig/webpack.prod.config')
const path = require('path')

module.exports = {
  lintOnSave: false,
  publicPath: "",
  devServer: {
    historyApiFallback: true,
    proxy: {
      '/api': {
        target: 'http://tmallapi.bluemoon.com.cn/bmcrm-control',
        changeOrigin: true,
        pathRewrite: {
          '/api': ''
        }
      }
    }
  },
  css: {
    loaderOptions: {
      less: {
        javascriptEnabled: true
      }
    }
  },
  configureWebpack:
    process.env.NODE_ENV === 'production'
      ? prodWebpackConfig
      : devWebpackConfig,
  chainWebpack: (config) => {
    config.plugins.delete('fork-ts-checker')
  }
}

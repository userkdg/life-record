var webpack = require('webpack')
var merge = require('webpack-merge')
var baseWebpackConfig = require('./webpack.base.config')
const WebpackInlineManifestPlugin = require('webpack-inline-manifest-plugin')
const PublishPlugin = require('../PublishPlugin')

const isPublish = (function() {
  const argv = JSON.parse(process.env.npm_config_argv)
  if (argv && argv.original && argv.original.length > 1) {
    const original = argv.original
    return original[1] === 'devbuild' && original.find((e) => e === '--push')
  }
  return false
})()

module.exports = merge(baseWebpackConfig, {
  // devtool: 'none',
  optimization: {
    moduleIds: 'hashed',
    runtimeChunk: {
      name: 'webpackManifest'
    },
    splitChunks: {
      chunks: 'all',
      cacheGroups: {
        vendor: {
          priority: -10,
          test: /[\\/]node_modules[\\/]/
        }
        // manifest: {
        //   minChunks: 1,
        //   priority: -20,
        //   reuseExistingChunk: true
        // }
      }
    }
  },
  plugins: [
    new webpack.HashedModuleIdsPlugin(),
    new WebpackInlineManifestPlugin({
      name: 'webpackManifest'
    }),
    new PublishPlugin({
      isPublish,
      host: '192.168.236.2',
      port: 32200,
      username: 'appadm',
      password: '1lt3rulv@tAs'
    })
  ]
})

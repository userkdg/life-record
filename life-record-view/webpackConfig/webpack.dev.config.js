var webpack = require('webpack')
var merge = require('webpack-merge')
var baseWebpackConfig = require('./webpack.base.config')
var FriendlyErrorsPlugin = require('friendly-errors-webpack-plugin')
/* const { BundleAnalyzerPlugin } = require('webpack-bundle-analyzer') */

module.exports = merge(baseWebpackConfig, {
  plugins: [
    new webpack.NoEmitOnErrorsPlugin(),
    new FriendlyErrorsPlugin(),
    /* new BundleAnalyzerPlugin({
      analyzerMode: 'server',
      analyzerPort: 8899,
      reportFilename: 'report.html',
      defaultSizes: 'parsed',
      openAnalyzer: true,
      generateStatsFile: false,
      statsFilename: 'stats.json',
      statsOptions: null,
      logLevel: 'info'
    }) */
  ]
})

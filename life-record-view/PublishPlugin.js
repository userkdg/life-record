/**
 * 手动上传需要把dist目录下的文件上传到远程目录/data/static/FE/dataAssetPlatform
 * 这里通过nodejs操作上传，简化繁琐的部署流程
 */
const SftpUpload = require('sftp-upload')
const colors = require('colors/safe')

const options = {
  path: './dist',
  remoteDir: '/data/static/FE/dataAssetPlatform'
}

// 上传本地目录下文件
const upload = config => {
  const sftp = new SftpUpload(config)
  return new Promise(resolve => {
    sftp
      .on('error', function(err) {
        throw err
      })
      .on('uploading', function(progress) {
        console.log('Uploading', colors.yellow(progress.file))
        console.log(colors.blue(progress.percent + '% completed'))
      })
      .on('completed', function() {
        console.log(colors.green('Upload Completed'))
        resolve(sftp)
      })
      .upload()
  })
}

class PublishPlugin {
  constructor(options) {
    this.options = options
  }
  apply(compiler) {
    compiler.hooks.done.tap('PublishPlugin', compiler => {
      const { host, port, username, password, isPublish } = this.options
      if (isPublish) {
        upload({ host, port, username, password, ...options })
      }
    })
  }
}

module.exports = PublishPlugin

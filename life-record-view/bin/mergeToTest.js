var shell = require('shelljs')

function errorMsg (msg) {
  shell.echo(msg);
  shell.exit(1);
}

if (!shell.which('git')) {
  shell.echo('Sorry, this script requires git');
  shell.exit(1);
}

// Run external tool synchronously
// if (shell.exec('git status').code !== 0) {
//   shell.echo('Error: Git status failed');
//   shell.exit(1);
// }

var str = shell.exec('git branch').toString()
var reg = /\* (.+)\n/g
var u = reg.exec(str)
var currentBranch = ''
if (u && u.length >= 2) {
  currentBranch = u[1]
}
if (shell.exec('git checkout test').code !== 0) {
  errorMsg('Error: Git checkout test failed')
}
if (shell.exec('git pull origin test').code !== 0) {
  errorMsg('Error: Git pull origin test failed')
}
if (shell.exec('git merge ' + currentBranch).code !== 0) {
  errorMsg('Error: Git merge ' + currentBranch + ' failed')
}
if (shell.exec('git push origin test').code !== 0) {
  errorMsg('Error: Git push origin test failed')
}
if (shell.exec('git checkout ' + currentBranch).code !== 0) {
  errorMsg('Error: Git checkout ' + currentBranch + ' failed')
}


// Run external tool synchronously
// if (shell.exec('git commit -am "Auto-commit"').code !== 0) {
//   shell.echo('Error: Git commit failed');
//   shell.exit(1);
// }
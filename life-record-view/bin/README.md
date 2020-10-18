### 工具指令集

**bin** 目录下存放一些自定义的工具指令，可以自己添加一些适合自己操作的指令。

#### mergeToTest

##### 作用

将当前所在分支合并到test分支，并切换回来。如果遇到哪一步出错会停止下来。

##### 调用

```shell
npm run mergeToTest
```

##### 步骤

* 切换到 **test** 分支
* 更新 **test** 远程分支
* 合并刚刚的分支
* 将合并推送到远程 **test** 分支
* 切换回刚刚的分支


#### jkbuild

##### 作用

直接调用一次jenkins测试环境的打包（没错，就是懒......）。

##### 调用

```shell
npm run jkbuild
```

##### 注意

* 这个只是直接调一次jenkins测试环境的打包，不会主动去切换jenkins上要打包的分支，所以确保你已经切换到了你要测试的分支。
* 运行指令后没有任何提示就是表示成功。



#### mergeToTest:build

##### 作用

就是先执行 **mergeToTest** ，再执行 **jkbuild** 。（懒......）

##### 调用

```shell
npm run mergeToTest:build
```


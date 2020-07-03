# MVVM-Architecture

**Android端 MVVM + Jetpack 架构组件的Github客户端。**

* [English Document](https://github.com/qingmei2/MVVM-Architecture/blob/master/README_EN.md) | 中文文档

## 通知

这个项目采用了数种 `Mvvm` 的实现方式，你可以参考任意感兴趣的进行了解：

* [Jetpack + Coroutine + Dagger-Hilt](https://github.com/qingmei2/MVVM-Architecture) 
* [Jetpack + Coroutine + Kodein](https://github.com/qingmei2/MVVM-Architecture/tree/kodein_coroutine_livedata) 
* [Jetpack + RxJava + Kodein](https://github.com/qingmei2/MVVM-Architecture/tree/branch_rxjava) 

> 项目中并未使用 `DataBinding`，我个人对 `Mvvm` 的见解，请参考 [这里](https://github.com/qingmei2/MVVM-Architecture/issues/15)。

## 屏幕截图

<div align:left;display:inline;>
<img width="200" height="360" src="https://github.com/qingmei2/MVVM-Architecture/blob/master/screenshots/login.png"/>
<img width="200" height="360" src="https://github.com/qingmei2/MVVM-Architecture/blob/master/screenshots/home.png"/>
<img width="200" height="360" src="https://github.com/qingmei2/MVVM-Architecture/blob/master/screenshots/repos.png"/>
<img width="200" height="360" src="https://github.com/qingmei2/MVVM-Architecture/blob/master/screenshots/me.png"/>
</div>

## 三方组件

### Android Jetpack 组件

* [Lifecycle: Create a UI that automatically responds to lifecycle events.](https://developer.android.com/topic/libraries/architecture/lifecycle)

* [LiveData: Build data objects that notify views when the underlying database changes.](https://developer.android.com/topic/libraries/architecture/livedata)

* [ViewModel: Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.](https://developer.android.com/topic/libraries/architecture/viewmodel)

* [Room: Access your app's SQLite database with in-app objects and compile-time checks.](https://developer.android.com/topic/libraries/architecture/room)

* [Navigation: Handle everything needed for in-app navigation.](https://developer.android.com/topic/libraries/architecture/navigation/)

* [Paging: Makes it easier for you to load data gradually and gracefully within your app's RecyclerView.](https://developer.android.com/topic/libraries/architecture/paging/)

### 网络请求

* [Retrofit2: Type-safe HTTP client for Android and Java by Square, Inc.](https://github.com/square/retrofit)

* [OkHttp: An HTTP+HTTP/2 client for Android and Java applications.](https://github.com/square/okhttp)

### 依赖注入

* [Dagger-hilt: Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

* [~~Kodein-DI: Painless Kotlin Dependency Injection~~](https://github.com/Kodein-Framework/Kodein-DI)

### 其它

* [Glide: An image loading and caching library for Android focused on smooth scrolling](https://github.com/bumptech/glide)

* [Timber: A logger with a small, extensible API which provides utility on top of Android's normal Log class.](https://github.com/JakeWharton/timber)

<a id="usage"></a>

## 开始使用

如果编译遇到如下图的错误：

![](https://github.com/qingmei2/MVVM-Rhine/blob/master/screenshots/compile_error.png)

> 出现这个问题的原因，最新版本的代码，需要开发者注册一个自己的`OAuth Application`，注册后，`Github`的API访问次数就能达到5000次/小时（之前的版本只有60次/小时），之前很多朋友反应在Debug过程中不够用，断点打了几次就被限制请求了，因此最新版本添加了这个配置的步骤，虽然麻烦了一小步，但是对于学习效率的提升，这点配置时间可以忽略不计。

* 1.直接通过git命令行进行clone:

```shell
$ git clone https://github.com/qingmei2/MVVM-Rhine.git
```

* 2.注册你的GithubApp

首先打开[这个链接](https://github.com/settings/applications/new),注册属于你的`OAuth Application`：

<div align:left;display:inline;>
<img width="480" height="480" src="https://github.com/qingmei2/MVVM-Rhine/blob/master/screenshots/regist_step1.png"/>
</div>

注册完成后，记住下面的`Client ID`和`Client Secret`,并配置到你的项目根目录的`local.properties`文件中：

<div align:left;display:inline;>
<img width="550" height="384" src="https://github.com/qingmei2/MVVM-Rhine/blob/master/screenshots/regist_step2.png"/>
</div>

```groovy
CLIENT_ID = "xxxxxx"
CLIENT_SECRET = "xxxxxx"
```

大功告成，接下来点击编译并运行即可。:tada: :tada: :tada:

## 如何入手学习这个项目？

如何使用`Android Jetpack`？

>* [Android官方架构组件Lifecycle：生命周期组件详解&原理分析](https://juejin.im/post/5c53beaf51882562e27e5ad9)
>* [Android官方架构组件ViewModel:从前世今生到追本溯源](https://juejin.im/post/5c047fd3e51d45666017ff86)
>* [Android官方架构组件Paging：分页库的设计美学](https://juejin.im/post/5c53ad9e6fb9a049eb3c5cfd)
>* [Android官方架构组件Paging-Ex：为分页列表添加Header和Footer](https://juejin.im/post/5caa0052f265da24ea7d3c2c)
>* [Android官方架构组件Paging-Ex：列表状态的响应式管理](https://juejin.im/post/5ce6ba09e51d4555e372a562)
>* [Android官方架构组件Navigation：大巧不工的Fragment管理框架](https://juejin.im/post/5c53be3951882562d27416c6)
>* [Android官方架构组件LiveData: 观察者模式领域二三事（*）](https://juejin.im/post/5c25753af265da61561f5335)
>* [Android官方架构组件DataBinding-Ex:双向绑定篇（*）](https://juejin.im/post/5c3e04b7f265da611b589574)  

## 感谢

:art: 项目中的UI设计部分参考了 [gitme](https://github.com/flutterchina/gitme).

:star: 项目参考了 [rx-mvvm-android](https://github.com/ffgiraldez/rx-mvvm-android) 并对其部分代码进行了引用.

## 其他开源项目

> [MVI-Architecture: 基于Jetpack, 更加响应式&函数式的编程实践](https://github.com/qingmei2/MVI-Architecture)

> [RxImagePicker: 灵活的Android图片选择器，提供了知乎和微信主题的支持](https://github.com/qingmei2/RxImagePicker)

## License

    The MVVM-Rhine: Apache License

    Copyright (c) 2018 qingmei2

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

# MVVM-Architecture

**The MVVM arch using Coroutine and Android Jetpack.**

* English Documentation | [中文文档](https://github.com/qingmei2/MVVM-Architecture)

This repo provides two `Mvvm` architecture implementation, see:
 
* [Jetpack + Coroutine + Dagger-Hilt](https://github.com/qingmei2/MVVM-Architecture) 
* [Jetpack + Coroutine + Kodein](https://github.com/qingmei2/MVVM-Architecture/tree/kodein_coroutine_livedata) 
* [Jetpack + RxJava + Kodein](https://github.com/qingmei2/MVVM-Architecture/tree/branch_rxjava) 

## ScreenShots

<div align:left;display:inline;>
<img width="200" height="360" src="https://upload-images.jianshu.io/upload_images/7293029-17fd103f3c524a1c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/200"/>
<img width="200" height="360" src="https://upload-images.jianshu.io/upload_images/7293029-33af9e0ee5686851.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/200"/>
<img width="200" height="360" src="https://upload-images.jianshu.io/upload_images/7293029-177d808edca4f7ee.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/200"/>
<img width="200" height="360" src="https://upload-images.jianshu.io/upload_images/7293029-20e72a2bdfaa8f7e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/200"/>
</div>

## Libraries

### Android Jetpack

* [Lifecycle: Create a UI that automatically responds to lifecycle events.](https://developer.android.com/topic/libraries/architecture/lifecycle)

* [LiveData: Build data objects that notify views when the underlying database changes.](https://developer.android.com/topic/libraries/architecture/livedata)

* [ViewModel: Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.](https://developer.android.com/topic/libraries/architecture/viewmodel)

* [Room: Access your app's SQLite database with in-app objects and compile-time checks.](https://developer.android.com/topic/libraries/architecture/room)

* [Navigation: Handle everything needed for in-app navigation.](https://developer.android.com/topic/libraries/architecture/navigation/)

* [Paging3: Makes it easier for you to load data gradually and gracefully within your app's RecyclerView.](https://developer.android.com/topic/libraries/architecture/paging/)

* [DataStore: A data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. ](https://developer.android.google.cn/topic/libraries/architecture/datastore?hl=zh_cn)

### Http

* [Retrofit2: Type-safe HTTP client for Android and Java by Square, Inc.](https://github.com/square/retrofit)

* [OkHttp: An HTTP+HTTP/2 client for Android and Java applications.](https://github.com/square/okhttp)

### DI

* [~~Kodein-DI: Painless Kotlin Dependency Injection~~](https://github.com/Kodein-Framework/Kodein-DI)

* [Dagger-hilt: Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

### Others

* [Glide: An image loading and caching library for Android focused on smooth scrolling](https://github.com/bumptech/glide)

* [Timber: A logger with a small, extensible API which provides utility on top of Android's normal Log class.](https://github.com/JakeWharton/timber)

## Usage

Step1: Fork this repo directly:

```shell
$ git clone https://github.com/qingmei2/MVVM-Architecture.git
```

Step2: [register](https://docs.github.com/cn/free-pro-team@latest/github/authenticating-to-github/creating-a-personal-access-token) OAuth Application and get access token.

Step3: Put the `USER_ACCESS_TOKEN` into `local.properties`:


```groovy
USER_ACCESS_TOKEN="xxxxxxxxxxxxxxxxxxxxxxxxx" // your access token
```

## Thanks to

:art: The UI design of this project refers to [gitme](https://github.com/flutterchina/gitme).

:star: This repo is inspired by [rx-mvvm-android](https://github.com/ffgiraldez/rx-mvvm-android) and uses some of its source code.

## Contributors

* [DaQinShgy](https://github.com/DaQinShgy)
* [GeorgCantor](https://github.com/GeorgCantor)

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

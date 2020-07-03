# MVVM-Architecture

**The MVVM arch using Coroutine and Android Jetpack.**

* English Documentation | [中文文档](https://github.com/qingmei2/MVVM-Architecture)

This repo provides two `Mvvm` architecture implementation, see:
 
* [Jetpack + Coroutine + Dagger-Hilt](https://github.com/qingmei2/MVVM-Architecture) 
* [Jetpack + Coroutine + Kodein](https://github.com/qingmei2/MVVM-Architecture/tree/kodein_coroutine_livedata) 
* [Jetpack + RxJava + Kodein](https://github.com/qingmei2/MVVM-Architecture/tree/branch_rxjava) 

## ScreenShots

<div align:left;display:inline;>
<img width="200" height="360" src="https://github.com/qingmei2/MVVM-Architecture/blob/master/screenshots/login.png"/>
<img width="200" height="360" src="https://github.com/qingmei2/MVVM-Architecture/blob/master/screenshots/home.png"/>
<img width="200" height="360" src="https://github.com/qingmei2/MVVM-Architecture/blob/master/screenshots/repos.png"/>
<img width="200" height="360" src="https://github.com/qingmei2/MVVM-Architecture/blob/master/screenshots/me.png"/>
</div>

## Libraries

### Android Jetpack

* [Lifecycle: Create a UI that automatically responds to lifecycle events.](https://developer.android.com/topic/libraries/architecture/lifecycle)

* [LiveData: Build data objects that notify views when the underlying database changes.](https://developer.android.com/topic/libraries/architecture/livedata)

* [ViewModel: Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.](https://developer.android.com/topic/libraries/architecture/viewmodel)

* [Room: Access your app's SQLite database with in-app objects and compile-time checks.](https://developer.android.com/topic/libraries/architecture/room)

* [Navigation: Handle everything needed for in-app navigation.](https://developer.android.com/topic/libraries/architecture/navigation/)

* [Paging: Makes it easier for you to load data gradually and gracefully within your app's RecyclerView.](https://developer.android.com/topic/libraries/architecture/paging/)

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
$ git clone https://github.com/qingmei2/MVVM-Rhine.git
```

Step2: [Register](https://github.com/settings/applications/new) OAuth Application.

<div align:left;display:inline;>
<img width="480" height="480" src="https://github.com/qingmei2/MVVM-Rhine/blob/master/screenshots/regist_step1.png"/>
</div>

Step3: Put the `Client ID` and the `Client Secret` into `local.properties`:

<div align:left;display:inline;>
<img width="550" height="384" src="https://github.com/qingmei2/MVVM-Rhine/blob/master/screenshots/regist_step2.png"/>
</div>

```groovy
CLIENT_ID = "xxxxxx"
CLIENT_SECRET = "xxxxxx"
```

## Thanks to

:art: The UI design of this project refers to [gitme](https://github.com/flutterchina/gitme).

:star: This repo is inspired by [rx-mvvm-android](https://github.com/ffgiraldez/rx-mvvm-android) and uses some of its source code.

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

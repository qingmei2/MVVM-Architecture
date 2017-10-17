# MvvmArchitecture

MVVM模式的开发框架。

### 开发环境：

AndroidStudio 3.0 Beta7

### 网络层：

HTTP : Rxjava2 + Retrofit2 + Okhttp

ImageLoader:Glide v4

Cache: RxCache

### 数据处理层：

JavaBean: lombok

Java8：Stream + Lambda

Dependencies Injection: DaggerAndroid

### UI层：

Views Binding: DataBinding

List: RecyclerView + MultiType

Layout:ConstraintLayout

## 常见问题

【Q】我的编译不通过怎么回事？

请首先查看您的编译环境，gradle plugin版本是否为3.0以上；其次您需要为您的AndroidStudio 安装lombok插件.

【Q】不使用ButterKnife吗？

个人认为DataBinding本身就是将View相关的属性都绑定在了xml文件中，ButterKnife使用机会很少，因此没有使用，如有需要，可依据个人喜好添加。


如果还未能编译成功，请提issue，我将第一时间为您解答。

object Versions {
    const val android_plugin = "4.0.0"
    const val kotlin = "1.3.72"
    const val kotlin_coroutines = "1.3.3"

    const val appcompat = "1.1.0"
    const val jetpack_lifecycle = "2.2.0-rc03"
    const val jetpack_viewModel = "2.2.0-rc03"
    const val jetpack_room = "2.2.3"
    const val jetpack_navigation = "2.2.0-rc04"
    const val jetpack_paging = "2.1.1"

    const val jetpack_activity = "1.2.0-alpha02"
    const val jetpack_fragment = "1.3.0-alpha02"

    const val retrofit = "2.7.1"
    const val dagger = "2.28-alpha"
    const val dagger_lifecycle = "1.0.0-alpha01"
    const val glide = "4.10.0"

    const val rxjava = "2.2.4"
    const val rxkotlin = "2.2.0"
    const val rxandroid = "2.1.0"
    const val rxbinding = "3.1.0"
    const val autodispose = "1.2.0"

    const val leakcanary = "1.6.2"

    const val espresso = "3.2.0"
    const val robolectric = "3.3.2"
}

object BuildVersions {
    const val compileSdkVersion = 29
    const val minSdkVersion = 21
    const val targetSdkVersion = 29
    const val versionCode = 8
    const val versionName = "0.6.0"
}

object BuildPlugins {
    const val androidGradle = "com.android.tools.build:gradle:${Versions.android_plugin}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val daggerGradle = "com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger}"
}

object Dependencies {
    const val kotlinLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinCoroutinesLibrary = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"

    const val appcompatV4 = "androidx.legacy:legacy-support-v4:${Versions.appcompat}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val appcompatV13 = "androidx.legacy:legacy-support-v13:${Versions.appcompat}"
    const val materialDesign = "com.google.android.material:material:1.2.0-alpha03"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.appcompat}"
    const val cardview = "androidx.cardview:cardview:${Versions.appcompat}"
    const val annotations = "androidx.annotation:annotation:${Versions.appcompat}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-alpha03"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val flexbox = "com.google.android:flexbox:1.1.0"

    const val activity = "androidx.activity:activity:${Versions.jetpack_activity}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.jetpack_activity}"
    const val fragment = "androidx.fragment:fragment:${Versions.jetpack_fragment}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.jetpack_fragment}"
    const val fragmentTest = "androidx.fragment:fragment-testing:${Versions.jetpack_fragment}"

    const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.jetpack_lifecycle}"
    const val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.jetpack_lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.jetpack_lifecycle}"
    const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.jetpack_lifecycle}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.jetpack_viewModel}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.jetpack_viewModel}"

    const val livedata = "androidx.lifecycle:lifecycle-livedata:${Versions.jetpack_lifecycle}"
    const val livedataRx = "androidx.lifecycle:lifecycle-reactivestreams:${Versions.jetpack_lifecycle}"
    const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.jetpack_lifecycle}"

    const val navigation = "androidx.navigation:navigation-fragment:${Versions.jetpack_navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui:${Versions.jetpack_navigation}"

    const val paging = "androidx.paging:paging-runtime:${Versions.jetpack_paging}"
    const val pagingRx = "androidx.paging:paging-rxjava2:${Versions.jetpack_paging}"

    const val room = "androidx.room:room-runtime:${Versions.jetpack_room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.jetpack_room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.jetpack_room}"
    const val roomRx = "androidx.room:room-rxjava2:${Versions.jetpack_room}"
    const val roomTesting = "androidx.room:room-testing:${Versions.jetpack_room}"

    const val okhttp = "com.squareup.okhttp3:okhttp:4.3.0"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:4.3.0"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitRx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"

    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val rxandroid = "com.squareup.retrofit2:rxandroid:${Versions.rxandroid}"
    const val rxkotlin = "com.squareup.retrofit2:rxkotlin:${Versions.rxkotlin}"

    const val rxbinding = "com.jakewharton.rxbinding3:rxbinding-core:${Versions.rxbinding}"
    const val rxbindingAppcompat = "com.jakewharton.rxbinding3:rxbinding-appcompat:${Versions.rxbinding}"
    const val rxbindingRecyclerview = "com.jakewharton.rxbinding3:rxbinding-recyclerview:${Versions.rxbinding}"
    const val rxbindingSwipeRefresh = "com.jakewharton.rxbinding3:rxbinding-swiperefreshlayout:${Versions.rxbinding}"

    const val autoDispose = "com.uber.autodispose:autodispose-android:${Versions.autodispose}"
    const val autoDisposeKtx = "com.uber.autodispose:autodispose-ktx:${Versions.autodispose}"
    const val autoDisposeAndroidKtx = "com.uber.autodispose:autodispose-android-ktx:${Versions.autodispose}"
    const val autoDisposeArchsKtx = "com.uber.autodispose:autodispose-android-archcomponents-ktx:${Versions.autodispose}"

    const val daggerHilt= "com.google.dagger:hilt-android:${Versions.dagger}"
    const val daggerAndroidCompiler= "com.google.dagger:hilt-android-compiler:${Versions.dagger}"

    const val daggerViewModel= "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.dagger_lifecycle}"
    const val daggerHiltCompiler= "androidx.hilt:hilt-compiler:${Versions.dagger_lifecycle}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val leakCanaryDebug = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
    const val leakCanaryDebugSupport = "com.squareup.leakcanary:leakcanary-support-fragment:${Versions.leakcanary}"
    const val leakCanaryRelease = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakcanary}"

    const val junit4 = "junit:junit:4.13"

    const val mockito_kotlin = "com.nhaarman:mockito-kotlin:1.5.0"

    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val robolectricV4 = "org.robolectric:shadows-support-v4:${Versions.robolectric}"

    const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:3.2.0"
    const val espressoIdlingResource = "androidx.test.espresso:espresso-idling-resource:3.2.0"
    const val testRunner = "androidx.test:runner:1.1.0"
    const val testRules = "androidx.test:rules:1.1.0"
}

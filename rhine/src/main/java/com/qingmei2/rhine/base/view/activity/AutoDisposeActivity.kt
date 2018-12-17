package com.qingmei2.rhine.base.view.activity

import androidx.appcompat.app.AppCompatActivity
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

open class AutoDisposeActivity : AppCompatActivity() {

    val scopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }
}
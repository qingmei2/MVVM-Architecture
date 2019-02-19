package com.qingmei2.rhine.base.view.activity

import androidx.appcompat.app.AppCompatActivity
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class AutoDisposeActivity : AppCompatActivity() {

    protected val scopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }
}
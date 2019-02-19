package com.qingmei2.rhine.base.view.fragment

import androidx.fragment.app.Fragment
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class AutoDisposeFragment : Fragment() {

    protected val scopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }
}
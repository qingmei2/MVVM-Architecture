package com.qingmei2.rhine.base.view.fragment

import androidx.fragment.app.Fragment
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

open class AutoDisposeFragment : Fragment() {

    val scopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }
}
package com.qingmei2.rhine.base.viewdelegate

import androidx.lifecycle.DefaultLifecycleObserver

interface IViewDelegate : DefaultLifecycleObserver {

    companion object {

        fun empty() = EmptyViewDelegate
    }
}

object EmptyViewDelegate : IViewDelegate
package com.qingmei2.sample.main

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import com.qingmei2.rhine.base.viewmodel.RhineViewModel
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.rhine.http.entity.UserInfo
import io.reactivex.Flowable

class MainViewModel : RhineViewModel()
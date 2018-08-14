package com.qingmei2.rhine.base

import android.databinding.ObservableField

import com.qingmei2.rhine.di.scheduler.SchedulerProvider
import com.qingmei2.rhine.http.service.ServiceManager

import javax.inject.Inject

import com.qingmei2.rhine.base.BaseViewModel.State.LOAD_WAIT


/**
 * Created by QingMei on 2017/10/14.
 * desc:
 */

open class BaseViewModel {

    @Inject
    var serviceManager: ServiceManager? = null
    @Inject
    var schedulers: SchedulerProvider? = null

    val loadingState = ObservableField(LOAD_WAIT)

    enum class State {
        /**
         * the state waiting for fetch data from server.
         */
        LOAD_WAIT,
        /**
         * the state is fetching data from server.
         */
        LOAD_ING,
        /**
         * fetch data successful
         */
        LOAD_SUCCESS,
        /**
         * fetch data faild
         */
        LOAD_FAILED
    }

}

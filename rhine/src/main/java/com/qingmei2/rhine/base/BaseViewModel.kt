package com.qingmei2.rhine.base

import android.databinding.ObservableField
import com.qingmei2.rhine.base.BaseViewModel.State.LOAD_WAIT
import com.qingmei2.rhine.http.service.ServiceManager
import com.qingmei2.rxschedulers.SchedulerProvider

open class BaseViewModel {

    var serviceManager: ServiceManager? = null
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

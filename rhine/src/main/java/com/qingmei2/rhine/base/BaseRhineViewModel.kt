package com.qingmei2.rhine.base

import android.databinding.ObservableField
import com.qingmei2.rhine.base.BaseRhineViewModel.State.LOAD_WAIT
import com.qingmei2.rhine.http.service.ServiceManager
import com.qingmei2.rxschedulers.SchedulerProvider
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

open class BaseRhineViewModel : KodeinAware {

    override val kodein: Kodein = BaseApplication.INSTANCE.kodein

    protected val serviceManager: ServiceManager by instance()

    protected val schedulers: SchedulerProvider by instance()

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

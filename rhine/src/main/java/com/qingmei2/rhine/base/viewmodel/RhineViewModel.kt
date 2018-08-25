package com.qingmei2.rhine.base.viewmodel

import com.qingmei2.rhine.base.BaseApplication
import com.qingmei2.rhine.http.service.ServiceManager
import com.qingmei2.rxschedulers.SchedulerProvider
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

open class RhineViewModel : LifecycleViewModel(), KodeinAware {

    override val kodein: Kodein = BaseApplication.INSTANCE.kodein

    protected val serviceManager: ServiceManager by instance()

    protected val schedulers: SchedulerProvider by instance()
}
package com.qingmei2.sample.base

import com.qingmei2.rhine.base.viewmodel.LifecycleViewModel
import com.qingmei2.sample.http.service.ServiceManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

open class BaseViewModel : LifecycleViewModel(), KodeinAware {

    override val kodein: Kodein = BaseApplication.INSTANCE.kodein

    protected val serviceManager: ServiceManager by instance()
}
package com.qingmei2.rhine.testframework.basekt

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.qingmei2.rhine.testframework.tools.RxSchedulerRule
import com.qingmei2.rhine.base.BaseViewModel
import com.qingmei2.rhine.testframework.tools.RxTestSchedulers
import org.junit.Before
import org.junit.Rule

/**
 * Created by QingMei on 2017/11/7.
 * desc:
 */
open class  BaseTestViewModel : BaseViewModel() {


    @Rule
    @JvmField
    val rxRule = RxSchedulerRule()

    @Before
    open fun setUp() {
        injectMock()
    }

    fun BaseViewModel.injectMock() {
        this.serviceManager = mock()
        this.schedulers = RxTestSchedulers()
    }
}
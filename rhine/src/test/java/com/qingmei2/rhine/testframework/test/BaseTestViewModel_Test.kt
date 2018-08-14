package com.qingmei2.rhine.testframework.test

import com.qingmei2.rhine.testframework.basekt.BaseTestViewModel
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test
import java.util.concurrent.TimeUnit
import org.mockito.Mockito as whenever

/**
 * Created by QingMei on 2017/11/8.
 * desc:
 */

class BaseTestViewModel_Test : BaseTestViewModel() {

    @Test
    fun testRxScheduler_normalCase() {
        Observable.just("test")
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(::print)
    }

    @Test
    fun testRxDelay() {
        val testObserver = TestObserver<Long>()
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(testObserver)

        //将时间轴跳到第6秒，执行发生的事件
        rxRule.advanceTimeTo(6, TimeUnit.SECONDS)
        testObserver.assertValueCount(6)
    }
}

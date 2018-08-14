package com.qingmei2.rhine.testframework.tools

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.TimeUnit

/**
 * Created by QingMei on 2017/11/10.
 * desc:
 */
class RxSchedulerRule : TestRule {

    private val testScheduler: TestScheduler = TestScheduler()

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler { testScheduler }
                RxJavaPlugins.setInitComputationSchedulerHandler { testScheduler }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { testScheduler }
                RxJavaPlugins.setInitSingleSchedulerHandler { testScheduler }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }

                try {
                    base?.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }

    fun advanceTimeTo(delayTime: Long, timeUnit: TimeUnit) {
        testScheduler.advanceTimeTo(delayTime, timeUnit)
    }

    fun advanceTimeBy(delayTime: Long, timeUnit: TimeUnit) {
        testScheduler.advanceTimeBy(delayTime, timeUnit)
    }
}
package com.qingmei2.sample.ui.main.common

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.RecyclerView
import arrow.core.none
import arrow.core.some
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.sample.base.BaseViewModel
import com.qingmei2.sample.http.RxSchedulers
import io.reactivex.Observable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.PublishSubject

/**
 * Common ViewModel, see:
 * [com.qingmei2.sample.ui.main.home.presentation.HomeFragment]
 * [com.qingmei2.sample.ui.main.repos.presentation.ReposFragment]
 */
@SuppressLint("CheckResult")
class FabAnimateViewModel : BaseViewModel() {

    val visibleState: MutableLiveData<Boolean> = MutableLiveData()

    val stateChangesConsumer: (Int) -> Unit = {
        scrollStateSubject.onNext(it)
    }

    private val scrollStateSubject: PublishSubject<Int> = PublishSubject.create()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        scrollStateSubject
                .map { it == RecyclerView.SCROLL_STATE_IDLE }
                .compose { upstream ->
                    upstream.zipWith(upstream.startWith(true)) { last, current ->
                        when (last == current) {
                            true -> none<Boolean>()
                            false -> current.some()
                        }
                    }
                }
                .flatMap { changed ->
                    changed.fold({
                        Observable.empty<Boolean>()
                    }, {
                        Observable.just(it)
                    })
                }
                .observeOn(RxSchedulers.ui)
                .bindLifecycle(this)
                .subscribe {
                    applyState(visible = it)
                }
    }

    private fun applyState(visible: Boolean) {
        visibleState.postValue(visible)
    }
}
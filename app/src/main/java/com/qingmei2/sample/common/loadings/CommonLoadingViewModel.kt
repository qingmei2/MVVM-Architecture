package com.qingmei2.sample.common.loadings

import android.arch.lifecycle.LifecycleOwner
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.widget.placeholderview.ILoadingViewController
import com.qingmei2.rhine.widget.placeholderview.LoadingView
import com.qingmei2.sample.base.BaseViewModel
import io.reactivex.subjects.PublishSubject

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class CommonLoadingViewModel private constructor(
        private val loadingView: LoadingView
) : BaseViewModel(), ILoadingViewController by loadingView {

    private val loadingState: PublishSubject<CommonLoadingState> = PublishSubject.create()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        loadingState
                .doFinally { loadingView.destroyPlaceholders() }
                .bindLifecycle(this)
                .subscribe { state ->
                    when (state) {
                        CommonLoadingState.LayoutError -> loadingView.showErrorView()
                        CommonLoadingState.LayoutEmpty -> loadingView.showEmptyView()
                        CommonLoadingState.LayoutLoading -> loadingView.showLoadingView()
                        CommonLoadingState.LayoutContent -> loadingView.showContentView()
                    }
                }
    }

    fun applyState(state: CommonLoadingState) = loadingState.onNext(state)

    companion object {

        fun instance(loadingView: LoadingView) = CommonLoadingViewModel(loadingView)
    }
}

enum class CommonLoadingState {

    LayoutError,

    LayoutEmpty,

    LayoutLoading,

    LayoutContent
}
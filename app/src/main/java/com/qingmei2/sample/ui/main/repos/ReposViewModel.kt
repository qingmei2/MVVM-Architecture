package com.qingmei2.sample.ui.main.repos

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.entity.Repo
import com.uber.autodispose.autoDisposable
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

@SuppressWarnings("checkResult")
class ReposViewModel(
        private val repo: ReposRepository
) : BaseViewModel() {

    // 排序条件的状态，每当该属性发生变化，页面会响应刷新事件
    val sortChangedEventSubject = BehaviorSubject.createDefault(sortByUpdate)
    // 刷新状态
    val refreshStateChangedSubject = PublishSubject.create<Boolean>()
    // PagedList
    val pagedListEventSubject = PublishSubject.create<PagedList<Repo>>()
    // 错误事件
    private val mErrorEventSubject = PublishSubject.create<Throwable>()
    // TODO 没有用到，实际业务场景中，每当分页数据被拉取，还有其他需求
    private val mReposPagedListEventSubject = PublishSubject.create<List<Repo>>()

    init {
        refreshStateChangedSubject
                .distinctUntilChanged()
                .filter { it }
                .autoDisposable(this)
                .subscribe { refreshDataSource() }

        sortChangedEventSubject
                .startWith(sortByUpdate)
                .distinctUntilChanged()
                .autoDisposable(this)
                .subscribe({
                    // onNext
                    refreshStateChangedSubject.onNext(true)
                }, {
                    // onError
                }, {
                    // onComplete
                }, {
                    // onSubscribe
                    refreshStateChangedSubject.onNext(true)
                })

        repo.subscribeRemoteRequestState()
                .observeOn(RxSchedulers.ui)
                .doOnNext { result ->
                    when (result) {
                        is Result.Loading -> applyState()
                        is Result.Idle -> applyState()
                        is Result.Failure -> {
                            applyState(error = result.error)
                            refreshStateChangedSubject.onNext(false)
                        }
                        is Result.Success -> {
                            refreshStateChangedSubject.onNext(false)
                        }
                    }
                }
                .autoDisposable(this)
                .subscribe()

        fetchPagedListFromDbCompletable()
                .toObservable()
                .autoDisposable(this)
                .subscribe(pagedListEventSubject)
    }

    private fun fetchPagedListFromDbCompletable(): Flowable<PagedList<Repo>> {
        return repo.fetchPagedListFromDb(sortChangedEventSubject.value ?: sortByUpdate)
                .subscribeOn(RxSchedulers.io)
    }

    fun refreshDataSource() {
        repo.refreshDataSource(sortChangedEventSubject.value ?: sortByUpdate)
    }

    override fun onCleared() {
        super.onCleared()

        repo.mAutoDisposeObserver.onNext(Unit)
        repo.mAutoDisposeObserver.onComplete()
    }

    private fun applyState(events: List<Repo>? = null,
                           error: Throwable? = null) {
        error?.apply { mErrorEventSubject.onNext(this) }
        events?.apply { mReposPagedListEventSubject.onNext(this) }
    }

    companion object {

        const val sortByCreated: String = "created"

        const val sortByUpdate: String = "updated"

        const val sortByLetter: String = "full_name"

        fun instance(fragment: Fragment,
                     repo: ReposRepository): ReposViewModel =
                ViewModelProviders
                        .of(fragment, ReposViewModelFactory.getInstance(repo))
                        .get(ReposViewModel::class.java)
    }
}

class ReposViewModelFactory(
        private val repo: ReposRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReposViewModel(repo) as T
    }

    companion object : SingletonHolderSingleArg<ReposViewModelFactory, ReposRepository>(::ReposViewModelFactory)
}
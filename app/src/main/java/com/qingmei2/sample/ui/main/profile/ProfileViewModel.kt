package com.qingmei2.sample.ui.main.profile

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import com.qingmei2.sample.entity.UserInfo
import com.qingmei2.sample.manager.UserManager
import io.reactivex.subjects.BehaviorSubject

class ProfileViewModel(
        private val repo: ProfileRepository
) : BaseViewModel() {

    private val mErrorEventSubject = BehaviorSubject.create<Throwable>()
    private val loadingStateChangedEventSubject = BehaviorSubject.create<Boolean>()
    val userInfoSubject = BehaviorSubject.create<UserInfo>()

    init {
        applyState(user = UserManager.INSTANCE)
    }

    private fun applyState(isLoading: Boolean = false,
                           user: UserInfo? = null,
                           error: Throwable? = null) {
        this.loadingStateChangedEventSubject.onNext(isLoading)
        error?.apply { mErrorEventSubject.onNext(this) }
        user?.apply { userInfoSubject.onNext(this) }
    }

    companion object {

        fun instance(fragment: Fragment, repo: ProfileRepository): ProfileViewModel =
                ViewModelProviders
                        .of(fragment, ProfileViewModelFactory.getInstance(repo))
                        .get(ProfileViewModel::class.java)
    }
}

class ProfileViewModelFactory(
        private val repo: ProfileRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repo) as T
    }

    companion object : SingletonHolderSingleArg<ProfileViewModelFactory, ProfileRepository>(::ProfileViewModelFactory)
}
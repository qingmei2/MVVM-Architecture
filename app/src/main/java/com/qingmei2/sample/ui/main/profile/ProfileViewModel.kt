package com.qingmei2.sample.ui.main.profile

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.util.SingletonHolderSingleArg
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ProfileViewModel(
        private val repo: ProfileRepository
) : BaseViewModel() {

    private val mViewStateSubject: BehaviorSubject<ProfileViewState> =
            BehaviorSubject.createDefault(ProfileViewState.initial())

    fun observeViewState(): Observable<ProfileViewState> {
        return mViewStateSubject.hide().distinctUntilChanged()
    }

    companion object {

        fun instance(fragment: Fragment, repo: ProfileRepository): ProfileViewModel =
                ViewModelProviders
                        .of(fragment, ProfileViewModelFactory(repo))
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
}
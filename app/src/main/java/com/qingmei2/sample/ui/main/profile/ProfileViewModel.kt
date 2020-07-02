package com.qingmei2.sample.ui.main.profile

import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel

class ProfileViewModel @ViewModelInject constructor(
        private val repo: ProfileRepository
) : BaseViewModel() {

    private val _viewStateLiveData: MutableLiveData<ProfileViewState> = MutableLiveData(ProfileViewState.initial())
    val viewStateLiveData: LiveData<ProfileViewState> = _viewStateLiveData

    companion object {

        fun instance(fragment: Fragment, repo: ProfileRepository): ProfileViewModel =
                ViewModelProvider(fragment, ProfileViewModelFactory(repo)).get(ProfileViewModel::class.java)
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

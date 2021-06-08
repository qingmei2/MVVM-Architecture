package com.qingmei2.sample.ui.main.profile

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val repo: ProfileRepository
) : BaseViewModel() {

    private val _viewStateLiveData: MutableLiveData<ProfileViewState> = MutableLiveData(ProfileViewState.initial())
    val viewStateLiveData: LiveData<ProfileViewState> = _viewStateLiveData
}
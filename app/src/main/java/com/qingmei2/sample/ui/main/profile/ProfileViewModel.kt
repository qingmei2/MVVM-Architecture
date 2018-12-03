package com.qingmei2.sample.ui.main.profile

import android.arch.lifecycle.*
import android.support.v4.app.FragmentActivity
import arrow.core.Option
import arrow.core.none
import arrow.core.toOption
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
import com.qingmei2.rhine.ext.arrow.whenNotNull
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.entity.LoginUser
import com.qingmei2.sample.manager.UserManager

class ProfileViewModel(
        private val repo: ProfileRepository
) : BaseViewModel() {

    val error: MutableLiveData<Option<Throwable>> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val user: MutableLiveData<LoginUser> = MutableLiveData()

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        super.onCreate(lifecycleOwner)
        applyState(user = UserManager.INSTANCE.toOption())
    }

    private fun applyState(isLoading: Boolean = false,
                           user: Option<LoginUser> = none(),
                           error: Option<Throwable> = none()) {
        this.loading.postValue(isLoading)
        this.error.postValue(error)

        user.whenNotNull {
            this.user.postValue(it)
        }
    }

    companion object {

        fun instance(activity: FragmentActivity, repo: ProfileRepository): ProfileViewModel =
                ViewModelProviders
                        .of(activity, ProfileViewModelFactory(repo))
                        .get(ProfileViewModel::class.java).apply {
                            addLifecycle(activity)
                        }
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
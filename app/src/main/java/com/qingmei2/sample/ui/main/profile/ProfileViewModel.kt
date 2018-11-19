package com.qingmei2.sample.ui.main.profile

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import arrow.core.Option
import arrow.core.none
import arrow.core.toOption
import com.qingmei2.rhine.ext.arrow.whenNotNull
import com.qingmei2.rhine.base.viewmodel.BaseViewModel
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
}
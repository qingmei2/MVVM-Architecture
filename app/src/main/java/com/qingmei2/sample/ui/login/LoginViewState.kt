package com.qingmei2.sample.ui.login

import com.qingmei2.sample.entity.UserInfo

data class LoginViewState(
        val isLoading: Boolean,
        val throwable: Throwable?,
        val loginInfo: UserInfo?,
        val autoLoginEvent: AutoLoginEvent?,
        val useAutoLoginEvent: Boolean      // the flag ensure login info display one time.
) {

    companion object {

        fun initial(): LoginViewState {
            return LoginViewState(
                    isLoading = false,
                    throwable = null,
                    loginInfo = null,
                    autoLoginEvent = null,
                    useAutoLoginEvent = true
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginViewState

        if (isLoading != other.isLoading) return false
        if (throwable != other.throwable) return false
        if (loginInfo != other.loginInfo) return false
        if (autoLoginEvent != other.autoLoginEvent) return false
        if (useAutoLoginEvent != other.useAutoLoginEvent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + (throwable?.hashCode() ?: 0)
        result = 31 * result + (loginInfo?.hashCode() ?: 0)
        result = 31 * result + (autoLoginEvent?.hashCode() ?: 0)
        result = 31 * result + useAutoLoginEvent.hashCode()
        return result
    }
}
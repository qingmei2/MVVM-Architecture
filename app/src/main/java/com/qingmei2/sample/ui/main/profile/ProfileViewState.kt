package com.qingmei2.sample.ui.main.profile

import com.qingmei2.sample.entity.UserInfo
import com.qingmei2.sample.manager.UserManager

data class ProfileViewState(
        val isLoading: Boolean,
        val throwable: Throwable?,
        val userInfo: UserInfo?
) {

    companion object {

        fun initial(): ProfileViewState {
            return ProfileViewState(
                    isLoading = false,
                    throwable = null,
                    userInfo = UserManager.INSTANCE
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileViewState

        if (isLoading != other.isLoading) return false
        if (throwable != other.throwable) return false
        if (userInfo != other.userInfo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + (throwable?.hashCode() ?: 0)
        result = 31 * result + (userInfo?.hashCode() ?: 0)
        return result
    }
}
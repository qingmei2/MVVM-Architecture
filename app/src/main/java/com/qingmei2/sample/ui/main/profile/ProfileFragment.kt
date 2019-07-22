package com.qingmei2.sample.ui.main.profile

import android.os.Bundle
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.reactivex.clicksThrottleFirst
import com.qingmei2.rhine.image.GlideApp
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.sample.R
import com.qingmei2.sample.utils.toast
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_profile.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(profileKodeinModule)
    }

    private val mViewModel: ProfileViewModel by instance()

    override val layoutId: Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        mViewModel.observeViewState()
                .observeOn(RxSchedulers.ui)
                .autoDisposable(scopeProvider)
                .subscribe(::onNewState)

        mBtnEdit.clicksThrottleFirst()
                .autoDisposable(scopeProvider)
                .subscribe { toast { "coming soon..." } }
    }

    private fun onNewState(state: ProfileViewState) {
        if (state.throwable != null) {
            // handle throwable.
        }

        if (state.userInfo != null) {
            GlideApp.with(context!!)
                    .load(state.userInfo.avatarUrl)
                    .apply(RequestOptions().circleCrop())
                    .into(mIvAvatar)

            mTvNickname.text = state.userInfo.name
            mTvBio.text = state.userInfo.bio
            mTvLocation.text = state.userInfo.location
        }
    }
}
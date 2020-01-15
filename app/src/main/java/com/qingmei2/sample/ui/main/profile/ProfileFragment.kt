package com.qingmei2.sample.ui.main.profile

import android.os.Bundle
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.architecture.core.image.GlideApp
import com.qingmei2.sample.R
import com.qingmei2.sample.utils.toast
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
        observe(mViewModel.viewStateLiveData, this::onNewState)

        mBtnEdit.setOnClickListener { toast { "coming soon..." } }
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
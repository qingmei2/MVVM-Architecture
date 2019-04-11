package com.qingmei2.sample.ui.main.profile

import com.bumptech.glide.request.RequestOptions
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.ext.reactivex.clicksThrottleFirst
import com.qingmei2.rhine.image.GlideApp
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.FragmentProfileBinding
import com.qingmei2.sample.entity.UserInfo
import com.qingmei2.sample.utils.toast
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_profile.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(profileKodeinModule)
    }

    private val mViewModel: ProfileViewModel by instance()

    override val layoutId: Int = R.layout.fragment_profile

    override fun initView() {
        binds()
    }

    private fun binds() {
        mViewModel.user.toReactiveStream()
                .autoDisposable(scopeProvider)
                .subscribe { renderUserOwner(it) }

        mBtnEdit.clicksThrottleFirst()
                .autoDisposable(scopeProvider)
                .subscribe { toast { "coming soon..." } }

    }

    private fun renderUserOwner(userInfo: UserInfo) {
        GlideApp.with(context!!)
                .load(userInfo.avatarUrl)
                .apply(RequestOptions().circleCrop())
                .into(mIvAvatar)

        mTvNickname.text = userInfo.name
        mTvBio.text = userInfo.bio
        mTvLocation.text = userInfo.location
    }
}
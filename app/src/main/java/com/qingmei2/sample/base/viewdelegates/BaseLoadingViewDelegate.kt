package com.qingmei2.sample.base.viewdelegates

import com.qingmei2.sample.common.loadings.CommonLoadingViewModel
import com.qingmei2.sample.common.loadings.ILoadingDelegate

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseLoadingViewDelegate(
        val loadingViewModel: CommonLoadingViewModel
) : BaseViewDelegate(), ILoadingDelegate by loadingViewModel
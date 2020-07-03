package com.qingmei2.architecture.core.base.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qingmei2.architecture.core.base.view.IView

abstract class BaseFragment : Fragment(), IView {

    private var mRootView: View? = null

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(layoutId, container, false)
        return mRootView!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
    }
}

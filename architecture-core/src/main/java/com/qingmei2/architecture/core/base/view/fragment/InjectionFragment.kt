package com.qingmei2.architecture.core.base.view.fragment

import androidx.fragment.app.Fragment
import com.qingmei2.architecture.core.base.view.IView
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext

abstract class InjectionFragment : Fragment(), KodeinAware, IView {

    protected val parentKodein by closestKodein()

    override val kodeinContext = kcontext<Fragment>(this)
}
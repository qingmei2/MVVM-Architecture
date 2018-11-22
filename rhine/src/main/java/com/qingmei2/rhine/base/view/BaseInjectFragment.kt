package com.qingmei2.rhine.base.view

import android.support.v4.app.Fragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.closestKodein
import org.kodein.di.generic.kcontext

abstract class BaseInjectFragment : Fragment(),
        KodeinAware, IView {

    protected val parentKodein by closestKodein()

    override val kodeinContext = kcontext<Fragment>(this)
}
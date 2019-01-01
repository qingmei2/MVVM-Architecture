package com.qingmei2.sample.ui.main

import android.annotation.SuppressLint
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.FragmentMainBinding
import com.qingmei2.sample.ui.main.home.HomeFragment
import com.qingmei2.sample.ui.main.profile.ProfileFragment
import com.qingmei2.sample.ui.main.repos.ReposFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

@Suppress("PLUGIN_WARNING")
@SuppressLint("CheckResult")
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(mainKodeinModule)
        bind<FragmentManager>() with instance(childFragmentManager)
    }

    override val layoutId: Int = R.layout.fragment_main

    val viewModel: MainViewModel by instance()

    fun initFragments(): List<Fragment> =
            listOf(HomeFragment(), ReposFragment(), ProfileFragment())

    // port-mode only
    fun onPageSelectChangedPort(index: Int) {
        for (position in 0..index) {
            if (navigation.visibility == View.VISIBLE)
                navigation.menu.getItem(position).isChecked = index == position
        }
    }

    // port-mode only
    fun onBottomNavigationSelectChanged(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                viewPager.currentItem = 0
            }
            R.id.nav_repos -> {
                viewPager.currentItem = 1
            }
            R.id.nav_profile -> {
                viewPager.currentItem = 2
            }
        }
    }

    // land-mode only
    fun onPageSelectChangedLand(index: Int) {
        if (viewPager.currentItem != index) {
            viewPager.currentItem = index
            closeFabMenuLand()
        }
    }

    // land-mode only
    private fun closeFabMenuLand() {
        if (fabMenu != null && fabMenu.isExpanded)
            fabMenu.toggle()
    }
}
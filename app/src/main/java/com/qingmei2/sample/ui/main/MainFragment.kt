package com.qingmei2.sample.ui.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.qingmei2.architecture.core.adapter.ViewPagerAdapter
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.sample.R
import com.qingmei2.sample.ui.main.home.HomeFragment
import com.qingmei2.sample.ui.main.profile.ProfileFragment
import com.qingmei2.sample.ui.main.repos.ReposFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@Suppress("PLUGIN_WARNING")
@SuppressLint("CheckResult")
@AndroidEntryPoint
class MainFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_main

    private val mViewModel: MainViewModel by viewModels()     // not used

    private var isPortMode: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPortMode = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        viewPager.adapter = ViewPagerAdapter(childFragmentManager,
                listOf(HomeFragment(), ReposFragment(), ProfileFragment()))
        viewPager.offscreenPageLimit = 2

        when (isPortMode) {
            true -> bindsPortScreen()
            false -> bindsLandScreen()
        }
    }

    private fun bindsPortScreen() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

            override fun onPageSelected(position: Int) = onPageSelectChanged(position)


            override fun onPageScrollStateChanged(state: Int) = Unit
        })
        navigation.setOnNavigationItemSelectedListener { menuItem ->
            onBottomNavigationSelectChanged(menuItem)
            true
        }
    }

    private fun bindsLandScreen() {
        fabHome.setOnClickListener { onPageSelectChanged(0) }
        fabRepo.setOnClickListener { onPageSelectChanged(1) }
        fabProfile.setOnClickListener { onPageSelectChanged(2) }
    }

    private fun onPageSelectChanged(index: Int) {
        // port-mode
        if (isPortMode) {
            for (position in 0..index) {
                if (navigation.visibility == View.VISIBLE)
                    navigation.menu.getItem(position).isChecked = index == position
            }
        } else {
            // land-mode
            if (viewPager.currentItem != index) {
                viewPager.currentItem = index
                if (fabMenu != null && fabMenu.isExpanded)
                    fabMenu.toggle()
            }
        }
    }

    // port-mode only
    private fun onBottomNavigationSelectChanged(menuItem: MenuItem) {
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
}

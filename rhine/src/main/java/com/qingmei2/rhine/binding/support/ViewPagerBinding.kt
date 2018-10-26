package com.qingmei2.rhine.binding.support

import android.databinding.BindingAdapter
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import arrow.core.toOption
import com.qingmei2.rhine.ext.arrow.whenEmpty
import com.qingmei2.rhine.functional.Consumer

interface ViewPagerConsumer : Consumer<Int>

@BindingAdapter(
        "bind_fragmentManager",
        "bind_fragments",
        "bind_offScreenPageLimit", requireAll = false)
fun bindViewPagerAdapter(viewPager: ViewPager,
                         fragmentManager: FragmentManager,
                         fragments: List<Fragment>,
                         pageLimit: Int?) {
    viewPager.adapter
            .toOption()
            .whenEmpty {
                viewPager.adapter = ViewPagerAdapter(fragmentManager, fragments)
            }
    viewPager.offscreenPageLimit = pageLimit ?: DEFAULT_OFF_SCREEN_PAGE_LIMIT
}

@BindingAdapter("bind_onPageSelectedChanged", requireAll = false)
fun onPageChangeListener(viewPager: ViewPager,
                         onPageSelected: ViewPagerConsumer) =
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) = onPageSelected.accept(position)


            override fun onPageScrollStateChanged(state: Int) {

            }
        })

class ViewPagerAdapter(fragmentManager: FragmentManager,
                       private val fragments: List<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(index: Int): Fragment = fragments[index]

    override fun getCount(): Int = fragments.size
}

const val DEFAULT_OFF_SCREEN_PAGE_LIMIT = 1
package com.qingmei2.sample.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.qingmei2.sample.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.apply {
            findFragmentByTag(TAG) ?: beginTransaction()
                    .add(R.id.flContainer, SearchFragment(), TAG)
                    .commitAllowingStateLoss()
        }
    }

    companion object {
        private const val TAG = "SearchFragment"

        fun launch(activity: FragmentActivity) = activity.apply {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

}

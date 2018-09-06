package com.qingmei2.sample.ui.main

import android.os.Bundle
import androidx.navigation.Navigation
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseActivity
import com.qingmei2.sample.databinding.ActivityMainBinding
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, allowOverride = true, copy = Copy.All)
        import(mainKodeinModule)
        bind<MainActivity>() with instance(this@MainActivity)
    }

    private val delegate: MainViewDelegate by instance()

    override val layoutId = R.layout.activity_main

    override fun onSupportNavigateUp(): Boolean =
            Navigation.findNavController(this, R.id.navHostFragment).navigateUp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.delegate = delegate
    }
}
package com.qingmei2.sample.main

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
        extend(parentKodein, copy = Copy.All)
        import(mainKodeinModule)
        bind<MainActivity>() with instance(this@MainActivity)
    }

    private val navigator: MainNavigator by instance()

    private val mainViewModel: MainViewModel by instance()

    private val delegate: MainViewDelegate = MainViewDelegate(
            mainViewModel,
            navigator
    ).apply {
        binding.delegate = this
    }


    override val layoutId = R.layout.activity_main

    override fun onSupportNavigateUp(): Boolean =
            Navigation.findNavController(this, R.id.navHostFragment).navigateUp()
}
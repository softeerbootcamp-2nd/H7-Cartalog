package com.softeer.cartalog.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.ActivityMainBinding
import com.softeer.cartalog.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController = binding.fcContainer.getFragment<NavHostFragment>().navController
        binding.navController = navController
        binding.mainActivity = this@MainActivity
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
    }

    fun changeTab(idx: Int){
        binding.tlStep.selectTab(binding.tlStep.getTabAt(idx))
    }
}
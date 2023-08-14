package com.softeer.cartalog.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.R
import com.softeer.cartalog.databinding.ActivityMainBinding
import com.softeer.cartalog.ui.dialog.PriceSummaryBottomSheetFragment
import com.softeer.cartalog.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val mainViewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    private val navController by lazy {
        binding.fcContainer.getFragment<NavHostFragment>().navController
    }
    private val bottomSheetFragment by lazy {
        PriceSummaryBottomSheetFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
        setTabSelected()

        binding.btnNext.setOnClickListener(this)
        binding.btnPrev.setOnClickListener(this)
        binding.btnPriceSummary.setOnClickListener(this)
    }

    private fun setTabSelected() {
        val font = ResourcesCompat.getFont(this, R.font.hyndaisans_head_bold)
        val tabLayout = binding.tlStep

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val selectedPosition = it.position

                    if (selectedPosition > mainViewModel.stepIndex.value!!) {

                        when (selectedPosition) {
                            0 -> navController.navigate(R.id.trimFragment)
                            1 -> navController.navigate(R.id.action_trimFragment_to_typeFragment)
                            2 -> navController.navigate(R.id.action_typeFragment_to_exteriorFragment)
                            3 -> navController.navigate(R.id.action_exteriorFragment_to_interiorFragment)
                            4 -> navController.navigate(R.id.action_interiorFragment_to_optionFragment)
                            5 -> navController.navigate(R.id.action_optionFragment_to_confirmFragment)
                        }

                        val tv = TextView(tabLayout.context).apply {
                            setTextColor(
                                ContextCompat.getColor(
                                    tabLayout.context,
                                    R.color.primary_color_200
                                )
                            )
                            typeface = font
                            gravity = Gravity.CENTER
                            text = tabLayout.getTabAt(selectedPosition - 1)?.text
                        }
                        tabLayout.getTabAt(selectedPosition - 1)?.customView = tv

                    } else {
                        when (selectedPosition) {
                            0 -> navController.navigate(R.id.action_typeFragment_to_trimFragment)
                            1 -> navController.navigate(R.id.action_exteriorFragment_to_typeFragment)
                            2 -> navController.navigate(R.id.action_interiorFragment_to_exteriorFragment)
                            3 -> navController.navigate(R.id.action_optionFragment_to_interiorFragment)
                            4 -> navController.navigate(R.id.action_confirmFragment_to_optionFragment)
                            5 -> navController.navigate(R.id.confirmFragment)
                        }
                        tabLayout.getTabAt(selectedPosition)?.customView = null
                    }
                    mainViewModel.setStepIndex(selectedPosition)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun changeTab(idx: Int){
        binding.tlStep.selectTab(binding.tlStep.getTabAt(idx))
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_next -> {
                changeTab(mainViewModel.stepIndex.value!! + 1)
            }

            R.id.btn_prev -> {
                changeTab(mainViewModel.stepIndex.value!! - 1)
            }

            R.id.btn_price_summary -> {
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }
        }
    }
}
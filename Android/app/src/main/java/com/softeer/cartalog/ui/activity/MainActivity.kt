package com.softeer.cartalog.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Process
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.R
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.databinding.ActivityMainBinding
import com.softeer.cartalog.ui.dialog.PriceSummaryBottomSheetFragment
import com.softeer.cartalog.util.PriceDataCallback
import com.softeer.cartalog.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener, PriceDataCallback {
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
    private var tabFont: Typeface? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
        tabFont = ResourcesCompat.getFont(this, R.font.hyndaisans_head_bold)

        setTabSelected()

        MyCarDatabase.getInstance(applicationContext)
        binding.btnExit.setOnClickListener(this)
        binding.btnSimilarEstimate.setOnClickListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTabSelected() {
        val tabLayout = binding.tlStep
        val tabStrip = tabLayout.getChildAt(0) as? LinearLayout

        (0..5).forEach {
            tabStrip?.getChildAt(it)?.setOnTouchListener { _, _ ->
                true
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val selectedPosition = it.position

                    // 처음부터 시작하기 눌렀을 때 동작
                    // TODO :: RoomDB 초기화 해야함!
                    if (mainViewModel.isReset.value == true) {
                        navController.navigate(R.id.trimFragment)
                        resetTabTextColor(tabLayout)
                        mainViewModel.changeResetState()
                    }

                    // 다음 탭 이동 동작
                    else if (selectedPosition > mainViewModel.stepIndex.value!!) {

                        when (selectedPosition) {
                            0 -> navController.navigate(R.id.trimFragment)
                            1 -> navController.navigate(R.id.action_trimFragment_to_typeFragment)
                            2 -> navController.navigate(R.id.action_typeFragment_to_exteriorFragment)
                            3 -> navController.navigate(R.id.action_exteriorFragment_to_interiorFragment)
                            4 -> navController.navigate(R.id.action_interiorFragment_to_optionFragment)
                            5 -> navController.navigate(R.id.action_optionFragment_to_confirmFragment)
                        }

                        setPrevTabTextColor(tabLayout, selectedPosition)
                    }

                    // 이전 탭 이동 동작
                    else {
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

    fun changeTab(idx: Int) {
        binding.tlStep.selectTab(binding.tlStep.getTabAt(idx))
    }

    fun setPrevTabTextColor(tabLayout: TabLayout, selectedPosition: Int) {
        val tv = TextView(tabLayout.context).apply {
            setTextColor(
                ContextCompat.getColor(tabLayout.context, R.color.primary_color_200)
            )
            typeface = tabFont
            gravity = Gravity.CENTER
            text = tabLayout.getTabAt(selectedPosition - 1)?.text
        }
        tabLayout.getTabAt(selectedPosition - 1)?.customView = null
        tabLayout.getTabAt(selectedPosition - 1)?.customView = tv
    }

    fun resetTabTextColor(tabLayout: TabLayout) {
        (0..5).forEach { tabLayout.getTabAt(it)?.customView = null }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_price_summary -> {
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }

            R.id.btn_exit -> {
                val dialogView =
                    LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog_exit, null)
                val dialog = AlertDialog.Builder(this@MainActivity)
                    .setView(dialogView)
                    .create()

                dialogView.findViewById<Button>(R.id.btn_exit)?.setOnClickListener {
                    Process.killProcess(Process.myPid())
                    finish()
                }
                dialogView.findViewById<Button>(R.id.btn_restart)?.setOnClickListener {
                    mainViewModel.changeResetState()
                    changeTab(0)
                    dialog.dismiss()
                }
                dialog.show()
            }

            R.id.btn_similar_estimate -> {
                startActivity(Intent(this@MainActivity, EstimateActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    override fun onInitPriceDataReceived(priceList: List<Int>) {
        mainViewModel.setMinMaxPrice(priceList[0], priceList[1])
        mainViewModel.setInitPriceData(priceList[2])
    }

    override fun changeUserTotalPrice(price: Int) {
        mainViewModel.setTotalPriceProgress(price)
    }

    fun getUserTotalPrice(): Int {
        return mainViewModel.totalPrice.value!!
    }


}
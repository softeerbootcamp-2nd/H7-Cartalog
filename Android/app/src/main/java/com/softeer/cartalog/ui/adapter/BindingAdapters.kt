package com.softeer.cartalog.ui.adapter

import android.view.Gravity
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.R
import com.softeer.cartalog.viewmodel.MainViewModel

@BindingAdapter("tabChange", "viewModel")
fun setOnTabChanged(
    tabLayout: TabLayout,
    navController: NavController,
    viewModel: MainViewModel
) {
    val font = ResourcesCompat.getFont(tabLayout.context, R.font.hyndaisans_head)

    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                val selectedPosition = it.position
                val tv = TextView(tabLayout.context)

                when (selectedPosition) {
                    0 -> navController.navigate(R.id.trimFragment)
                    1 -> navController.navigate(R.id.typeFragment)
                    2 -> navController.navigate(R.id.exteriorFragment)
                    3 -> navController.navigate(R.id.interiorFragment)
                    4 -> navController.navigate(R.id.optionFragment)
                    5 -> navController.navigate(R.id.confirmFragment)
                }

                if (selectedPosition > viewModel.stepIndex.value!!) {
                    tv.setTextColor(
                        ContextCompat.getColor(
                            tabLayout.context,
                            R.color.primary_color_200
                        )
                    )
                    tv.typeface = font
                    tv.gravity = Gravity.CENTER
                    tv.text = tabLayout.getTabAt(selectedPosition - 1)?.text
                    tabLayout.getTabAt(selectedPosition - 1)?.customView = tv
                } else {
                    tabLayout.getTabAt(selectedPosition)?.customView = null
                }
                viewModel.setStepIndex(selectedPosition)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
}
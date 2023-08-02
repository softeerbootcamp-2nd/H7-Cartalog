package com.softeer.cartalog.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.view.iterator
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.R
import java.text.FieldPosition

object BindingAdapters {
    @BindingAdapter("tabChange", "viewModel")
    @JvmStatic
    fun setOnTabChanged(tabLayout: TabLayout, navController: NavController, viewModel: ViewModel) {
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when(it.position){
                        0 -> navController.navigate(R.id.trimFragment)
                        1 -> navController.navigate(R.id.typeFragment)
                        2 -> navController.navigate(R.id.exteriorFragment)
                        3 -> navController.navigate(R.id.interiorFragment)
                        4 -> navController.navigate(R.id.optionFragment)
                        5 -> navController.navigate(R.id.confirmFragment)
                    }
                    val tv = TextView(tabLayout.context)

                    for (i in it.position-1 until it.position) {
                        tv.setTextColor(Color.BLUE)
                        tv.text = it.text
                        tabLayout.getTabAt(i)?.setCustomView(tv)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    @BindingAdapter("tabColorChange")
    @JvmStatic
    fun setTabColorChange(tabLayout: TabLayout, position: Int) {

    }
}
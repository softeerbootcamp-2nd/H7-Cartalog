package com.softeer.cartalog.ui.activity

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.softeer.cartalog.R
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.databinding.ActivityEstimateBinding
import com.softeer.cartalog.databinding.ActivityMainBinding
import com.softeer.cartalog.ui.dialog.PriceSummaryBottomSheetFragment
import com.softeer.cartalog.util.PriceDataCallback
import com.softeer.cartalog.viewmodel.MainViewModel

class EstimateActivity : AppCompatActivity() {

    private val binding: ActivityEstimateBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_estimate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnBack.setOnClickListener { finish() }
    }
}
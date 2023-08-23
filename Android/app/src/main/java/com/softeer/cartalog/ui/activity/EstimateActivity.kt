package com.softeer.cartalog.ui.activity

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.softeer.cartalog.R
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.ActivityEstimateBinding
import com.softeer.cartalog.databinding.ActivityMainBinding
import com.softeer.cartalog.ui.dialog.PriceSummaryBottomSheetFragment
import com.softeer.cartalog.util.PriceDataCallback
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.EstimateViewModel
import com.softeer.cartalog.viewmodel.MainViewModel
import com.softeer.cartalog.viewmodel.TrimViewModel

class EstimateActivity : AppCompatActivity() {

    private val binding: ActivityEstimateBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_estimate)
    }

    private val carRepositoryImpl by lazy {
        CarRepositoryImpl(
            CarLocalDataSource(MyCarDatabase.getInstance(this)!!),
            CarRemoteDataSource(RetrofitClient.carApi)
        )
    }
    private val estimateViewModel: EstimateViewModel by viewModels {
        CommonViewModelFactory(carRepositoryImpl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnBack.setOnClickListener { finish() }

        binding.viewModel = estimateViewModel
        binding.lifecycleOwner = this@EstimateActivity
    }
}
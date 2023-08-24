package com.softeer.cartalog.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.softeer.cartalog.R
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.ActivityEstimateBinding
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.EstimateViewModel

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

        estimateViewModel.message.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}
package com.softeer.cartalog.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.softeer.cartalog.R
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.ActivityEstimateBinding
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.EstimateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val totalPrice = intent.getIntExtra(MainActivity.TOTAL_PRICE, 0)
        val minPrice = intent.getIntExtra(MainActivity.MIN_PRICE, 0)
        val maxPrice = intent.getIntExtra(MainActivity.MAX_PRICE, 0)
        val estimateId = intent.getIntExtra(MainActivity.ESTIMATE_ID, 0)

        estimateViewModel.estimateId = estimateId
        estimateViewModel.setPriceData(totalPrice, minPrice, maxPrice)
        estimateViewModel.setData(2)

        binding.viewModel = estimateViewModel
        binding.lifecycleOwner = this@EstimateActivity

        binding.btnBack.setOnClickListener { finish() }
        binding.btnAddOptions.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                estimateViewModel.saveUserSelection()
                withContext(Dispatchers.Main) {
                    finish()
                }
            }

        }

        estimateViewModel.message.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}
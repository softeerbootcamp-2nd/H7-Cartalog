package com.softeer.cartalog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.estimate.EstimateRequest
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CarRepository) : ViewModel() {

    private val _stepIndex = MutableLiveData(0)
    val stepIndex: LiveData<Int> = _stepIndex
    private val _budgetRangeLimit = MutableLiveData(0)
    val budgetRangeLimit: LiveData<Int> = _budgetRangeLimit
    private val _budgetRangeLimitProgress = MutableLiveData(50)
    val budgetRangeLimitProgress: LiveData<Int> = _budgetRangeLimitProgress
    private val _totalPrice = MutableLiveData(0)
    val totalPrice: LiveData<Int> = _totalPrice
    private val _totalPriceProgress = MutableLiveData<Int>(0)
    val totalPriceProgress: LiveData<Int> = _totalPriceProgress
    private val _isExcess = MutableLiveData(false)
    val isExcess: LiveData<Boolean> = _isExcess

    private val _minPrice = MutableLiveData(0)
    val minPrice: LiveData<Int> = _minPrice
    private val _maxPrice = MutableLiveData(0)
    val maxPrice: LiveData<Int> = _maxPrice
    private var priceRange = 0
    private val _isReset = MutableLiveData<Boolean>(false)
    val isReset: LiveData<Boolean> = _isReset
    private val _isEstimateExist = MutableLiveData(false)
    val isEstimateExist: LiveData<Boolean> = _isEstimateExist

    fun setStepIndex(index: Int) {
        _stepIndex.value = index
    }

    fun setRangeLimit(progress: Int) {
        _budgetRangeLimitProgress.value = progress
        _budgetRangeLimit.value = calculatePriceFromProgress(progress)
        _isExcess.value = budgetRangeLimit.value!! < totalPrice.value!!
    }

    fun setMinMaxPrice(minPrice: Int, maxPrice: Int) {
        _minPrice.value = minPrice
        _maxPrice.value = maxPrice
        priceRange = maxPrice - minPrice
    }

    fun setTotalPriceProgress(total: Int) {
        _totalPrice.value = total
        _totalPriceProgress.value = calculateProgressFromPrice(totalPrice.value!!)
        _isExcess.value = budgetRangeLimit.value!! < totalPrice.value!!
    }

    private fun calculateProgressFromPrice(price: Int): Int {
        val adjustedValue = price - minPrice.value!!
        return (adjustedValue.toFloat() / priceRange.toFloat() * 100).toInt()
    }

    private fun calculatePriceFromProgress(progress: Int): Int {
        return minPrice.value!! + (priceRange * progress / 100)
    }

    fun setInitPriceData(total: Int) {
        _totalPrice.value = total
        _totalPriceProgress.value = calculateProgressFromPrice(totalPrice.value!!)
        _budgetRangeLimit.value = calculatePriceFromProgress(50)
    }

    fun changeResetState() {
        _isReset.value = !_isReset.value!!
        viewModelScope.launch {
            repository.deleteAllData()
        }
    }

    private fun setData(trimId: Int) {
        viewModelScope.launch {

            // 1. detail trim id 먼저 불러오기
            val selectPowerTrain = repository.getTypeData(PriceDataType.POWERTRAIN).optionId
            val selectBodyType = repository.getTypeData(PriceDataType.BODYTYPE).optionId
            val selectWheelDrive = repository.getTypeData(PriceDataType.WHEELDRIVE).optionId

            val modelTypeIds = "$selectPowerTrain,$selectBodyType,$selectWheelDrive"
            val detailTrimId = repository.getTrimDetail(modelTypeIds, trimId).detailTrimId

            // 2. 견적서 서버에 POST 후 내 견적서 정보 받아오기
            // 내, 외장 선택한 코드 가져오기
            val exteriorColorCode = repository.getTypeData(PriceDataType.EXTERIOR_COLOR).code ?: ""
            val interiorColorCode = repository.getTypeData(PriceDataType.INTERIOR_COLOR).code ?: ""

            // 옵션 선택한 것들 코드 가져오기
            val selectOptionOrPackageIds = repository.getOptionTypeDataList().map { it.code ?: "" }

            // 3. 견적서 업로드
            val estimate = EstimateRequest(
                detailTrimId,
                exteriorColorCode,
                interiorColorCode,
                selectOptionOrPackageIds
            )
            Log.d("TESTER", "$estimate")
            val estimateId = repository.postEstimate(estimate)
            Log.d("TESTER", "$estimateId")

            // 4. 견적서 번호를 통한 견적조회
            val estimateCounts = repository.getEstimateCount(estimateId)
            if (estimateCounts.similarEstimateCounts.isNotEmpty()) {
                _isEstimateExist.value = true
            }

        }
    }
}
package com.softeer.cartalog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.data.model.estimate.EstimateRequest
import com.softeer.cartalog.data.model.estimate.SimilarEstimateOption
import com.softeer.cartalog.data.model.estimate.SimilarEstimates
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class EstimateViewModel(private val repository: CarRepository) : ViewModel() {

    var detailTrimId = 0

    private var _estimateList = MutableLiveData<MutableList<SimilarEstimates>>(mutableListOf())
    val estimateList: LiveData<MutableList<SimilarEstimates>> = _estimateList

    private val _selectedCard = MutableLiveData(0)
    val selectedCard: LiveData<Int> = _selectedCard

    private val _selectedOptions =
        MutableLiveData<MutableList<SimilarEstimateOption>>(mutableListOf())
    val selectedOptions: LiveData<MutableList<SimilarEstimateOption>> = _selectedOptions

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _totalPrice = MutableLiveData(0)
    val totalPrice: LiveData<Int> = _totalPrice
    private val _totalPriceProgress = MutableLiveData<Int>(0)
    val totalPriceProgress: LiveData<Int> = _totalPriceProgress

    private val _estimatePrice = MutableLiveData(0)
    val estimatePrice: LiveData<Int> = _estimatePrice
    private val _estimatePriceProgress = MutableLiveData<Int>(0)
    val estimatePriceProgress: LiveData<Int> = _estimatePriceProgress

    private val _minPrice = MutableLiveData(0)
    val minPrice: LiveData<Int> = _minPrice
    private val _maxPrice = MutableLiveData(0)
    val maxPrice: LiveData<Int> = _maxPrice
    private var priceRange = 0

    init {
        setData(2)
    }

    fun showMessage(message: String) {
        _message.value = message
    }

    private fun setData(trimId: Int) {
        viewModelScope.launch {

            // 1. detail trim id 먼저 불러오기
            val selectPowerTrain = repository.getTypeData(PriceDataType.POWERTRAIN).optionId
            val selectBodyType = repository.getTypeData(PriceDataType.BODYTYPE).optionId
            val selectWheelDrive = repository.getTypeData(PriceDataType.WHEELDRIVE).optionId

            val modelTypeIds = "$selectPowerTrain,$selectBodyType,$selectWheelDrive"
            detailTrimId = repository.getTrimDetail(modelTypeIds, trimId).detailTrimId

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
//            val estimateId = repository.postEstimate(estimate)
//            Log.d("TESTER", "$estimateId")

            val estimateId = 1

            // 4. 견적서 번호를 통한 견적조회
            val estimateCounts = repository.getEstimateCount(estimateId)
            Log.d("TESTER", "$estimateCounts")

            // 5. 유사견적이 있으면 해당 유사견적을 확인하여 뷰모델에 저장하기
            if (estimateCounts.similarEstimateCounts.isNotEmpty()) {
                for (similar in estimateCounts.similarEstimateCounts) {
                    val similarEstimateItem =
                        repository.getSimilarEstimate(estimateId, similar.estimateId)
                    similarEstimateItem?.let {
                        _estimateList.value?.add(it)
                    }
                }
            }

            _estimateList.postValue(_estimateList.value)
            _estimateList.value?.toString()?.let { Log.d("TESTER", it) }

        }
    }

    fun changeSelectedCard(position: Int) {
        _selectedCard.value = position
    }

    fun addSelectedOption(option: SimilarEstimateOption) {
        var isSame = false
        for (options in _selectedOptions.value!!) {
            if (options.name == option.name) {
                isSame = true
            }
        }
        if (!isSame) {
            _selectedOptions.value?.add(option)
            _totalPrice.value = _totalPrice.value!!.plus(option.price)

        } else {
            showMessage("이미 추가하신 옵션입니다!")
        }
    }

    fun removeSelectedOption(option: SimilarEstimateOption) {
        _selectedOptions.value?.remove(option)
        _totalPrice.value = _totalPrice.value!!.minus(option.price)
    }

    fun setPriceData(total: Int, min: Int, max: Int) {
        _totalPrice.value = total
        _minPrice.value = min
        _maxPrice.value = max
        priceRange = max - min
        _totalPriceProgress.value = calculateProgressFromPrice(total)
    }

    fun setEstimatePrice(price: Int) {
        _estimatePrice.value = price
        _estimatePriceProgress.value = calculateProgressFromPrice(price)
    }

    private fun calculateProgressFromPrice(price: Int): Int {
        val adjustedValue = price - minPrice.value!!
        return (adjustedValue.toFloat() / priceRange.toFloat() * 100).toInt()
    }

    suspend fun saveUserSelection(){
        val optionList = selectedOptions.value!!.map {
            PriceData(
                carId = 1,
                optionType = PriceDataType.OPTION,
                optionId = null,
                name = it.name,
                price = it.price,
                imgUrl = it.imageUrl,
                code = it.optionId,
                isCheckedFromEstimate = true
            )
        }
        repository.setOptionTypeDataList(optionList)
        val old = repository.getMyCarData()
        val update = old!!.copy(
            totalPrice = totalPrice.value!!
        )
        repository.saveUserCarData(update)
    }
}

package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.confirm.ConfirmDetail
import com.softeer.cartalog.data.model.db.MyCar
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class ConfirmViewModel(private val repository: CarRepository) : ViewModel() {

    private val _carInfo = MutableLiveData<MyCar>()
    val carInfo: LiveData<MyCar> = _carInfo

    private val _detailPriceList = MutableLiveData<List<ConfirmDetail>>()
    val detailPriceList = _detailPriceList

    private val _userTotalPrice = MutableLiveData(0)
    val userTotalPrice: LiveData<Int> = _userTotalPrice

    private var modelTypeList: List<PriceData> = listOf()
    private var colorList: List<PriceData> = listOf()
    private var extraOptionList: List<PriceData> = listOf()

    init {
        setCarData()
    }

    fun setUserTotalPrice(price: Int) {
        _userTotalPrice.value = price
    }

    private fun setCarData() {
        viewModelScope.launch {
            _carInfo.value = repository.getMyCarData()
            val allPriceData = repository.getPriceDataList()
            modelTypeList = allPriceData.filter { priceData ->
                priceData.optionType == PriceDataType.POWERTRAIN || priceData.optionType == PriceDataType.BODYTYPE || priceData.optionType == PriceDataType.WHEELDRIVE
            }
            colorList = allPriceData.filter { priceData ->
                priceData.optionType == PriceDataType.EXTERIOR_COLOR || priceData.optionType == PriceDataType.INTERIOR_COLOR
            }
            extraOptionList = allPriceData.filter {priceData ->
                priceData.optionType == PriceDataType.OPTION
            }
            setDetailList()
        }
    }

    suspend fun getOptionDataChanged(){
        extraOptionList = repository.getOptionTypeDataList()
    }

    suspend fun setTotalPriceFromDB(){
        _userTotalPrice.postValue(repository.getTotalPrice())
    }

    fun setDetailList(){
            _detailPriceList.value = listOf(
                ConfirmDetail("모델 타입", modelTypeList),
                ConfirmDetail("색상", colorList),
                ConfirmDetail("추가 옵션", extraOptionList),
                ConfirmDetail("탁송", null),
                ConfirmDetail("할인 및 포인트", null),
                ConfirmDetail("면세 구분 및 등록비", null),
                ConfirmDetail("결제 수단", null),
                ConfirmDetail("안내 사항", null),
            )
    }
}
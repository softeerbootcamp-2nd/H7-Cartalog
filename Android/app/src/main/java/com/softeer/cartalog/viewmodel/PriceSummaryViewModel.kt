package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.summary.SummaryCarImage
import com.softeer.cartalog.data.model.summary.SummaryOptionPrice
import com.softeer.cartalog.data.model.db.MyCar
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class PriceSummaryViewModel(private val repository: CarRepository) : ViewModel() {

    private val _myCar = MutableLiveData<MyCar>()
    val myCar: LiveData<MyCar> = _myCar

    private val _powerTrain = MutableLiveData<SummaryOptionPrice>()
    val powerTrain: LiveData<SummaryOptionPrice> = _powerTrain

    private val _bodyType = MutableLiveData<SummaryOptionPrice>()
    val bodyType: LiveData<SummaryOptionPrice> = _bodyType

    private val _wheelDrive = MutableLiveData<SummaryOptionPrice>()
    val wheelDrive: LiveData<SummaryOptionPrice> = _wheelDrive

    private val _exterior = MutableLiveData<SummaryOptionPrice>()
    val exterior: LiveData<SummaryOptionPrice> = _exterior

    private val _interior = MutableLiveData<SummaryOptionPrice>()
    val interior: LiveData<SummaryOptionPrice> = _interior

    private val _options = MutableLiveData<MutableList<SummaryOptionPrice>>(mutableListOf())
    val options: LiveData<MutableList<SummaryOptionPrice>> = _options

    private val _carImage = MutableLiveData<SummaryCarImage>()
    val carImage: LiveData<SummaryCarImage> = _carImage

    private val _userTotalPrice = MutableLiveData(0)
    val userTotalPrice: LiveData<Int> = _userTotalPrice

    private var priceData: List<PriceData> = listOf()

    init {
        viewModelScope.launch {
            _myCar.value = repository.getMyCarData()
            priceData = repository.getPriceDataList()
            val colorCode = setOptionValues()
            _carImage.value = repository.getSummaryCarImage(colorCode.first, colorCode.second)
            setUserTotalPrice()
        }
    }

    private fun setOptionValues(): Pair<String, String> {

        var exteriorCode = ""
        var interiorCode = ""

        priceData.forEach {
            when (it.optionType) {
                PriceDataType.POWERTRAIN -> {
                    _powerTrain.value = SummaryOptionPrice(it.name, it.price)
                }

                PriceDataType.BODYTYPE -> {
                    _bodyType.value = SummaryOptionPrice(it.name, it.price)
                }

                PriceDataType.WHEELDRIVE -> {
                    _wheelDrive.value = SummaryOptionPrice(it.name, it.price)
                }

                PriceDataType.EXTERIOR_COLOR -> {
                    _exterior.value = SummaryOptionPrice(it.name, it.price)
                    exteriorCode = it.code.toString()
                }

                PriceDataType.INTERIOR_COLOR -> {
                    _interior.value = SummaryOptionPrice(it.name, it.price)
                    interiorCode = it.code.toString()
                }

                PriceDataType.OPTION -> {
                    _options.value?.add(
                        SummaryOptionPrice(it.name, it.price)
                    )
                    _options.postValue(_options.value)
                }
            }
        }
        return Pair(exteriorCode, interiorCode)
    }

    private fun setUserTotalPrice() {
        _userTotalPrice.value = myCar.value!!.minPrice + priceData.sumOf { it.price }
    }

}
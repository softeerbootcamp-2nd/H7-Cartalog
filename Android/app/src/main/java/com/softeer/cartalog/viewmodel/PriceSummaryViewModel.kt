package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.SummaryCarImage
import com.softeer.cartalog.data.model.db.MyCar
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class PriceSummaryViewModel(private val repository: CarRepository) : ViewModel() {

    private val _myCar = MutableLiveData<MyCar>()
    val myCar: LiveData<MyCar> = _myCar

    private val _priceData = MutableLiveData<List<PriceData>>()
    val priceData: LiveData<List<PriceData>> = _priceData

    private val _powerTrain = MutableLiveData<Pair<String, Int>>()
    val powerTrain: LiveData<Pair<String, Int>> = _powerTrain

    private val _bodyType = MutableLiveData<Pair<String, Int>>()
    val bodyType: LiveData<Pair<String, Int>> = _bodyType

    private val _wheelDrive = MutableLiveData<Pair<String, Int>>()
    val wheelDrive: LiveData<Pair<String, Int>> = _wheelDrive

    private val _exterior = MutableLiveData<Pair<String, Int>>()
    val exterior: LiveData<Pair<String, Int>> = _exterior

    private val _interior = MutableLiveData<Pair<String, Int>>()
    val interior: LiveData<Pair<String, Int>> = _interior

    private val _options = MutableLiveData<MutableList<Pair<String, Int>>>(mutableListOf())
    val options: LiveData<MutableList<Pair<String, Int>>> = _options

    private val _carImage = MutableLiveData<SummaryCarImage>()
    val carImage: LiveData<SummaryCarImage> = _carImage

    init {
        viewModelScope.launch {
            _myCar.value = repository.getMyCarData()
            _priceData.value = repository.getPirceDataList()
            val colorCode = setOptionValues()
            _carImage.value = repository.getSummaryCarImage(colorCode.first, colorCode.second)
        }
    }

    private fun setOptionValues(): Pair<String, String> {

        var exteriorCode = ""
        var interiorCode = ""

        _priceData.value?.forEach {
            when (it.optionType) {
                PriceDataType.POWERTRAIN -> {
                    _powerTrain.value = Pair(it.name, it.price)
                }

                PriceDataType.BODYTYPE -> {
                    _bodyType.value = Pair(it.name, it.price)
                }

                PriceDataType.WHEELDRIVE -> {
                    _wheelDrive.value = Pair(it.name, it.price)
                }

                PriceDataType.EXTERIOR_COLOR -> {
                    _exterior.value = Pair(it.name, it.price)
                    exteriorCode = it.colorCode.toString()
                }

                PriceDataType.INTERIOR_COLOR -> {
                    _interior.value = Pair(it.name, it.price)
                    interiorCode = it.colorCode.toString()
                }

                PriceDataType.OPTION -> {
                    _options.value?.add(
                        Pair(it.name, it.price)
                    )
                }
            }
        }

        return Pair(exteriorCode, interiorCode)
    }

}
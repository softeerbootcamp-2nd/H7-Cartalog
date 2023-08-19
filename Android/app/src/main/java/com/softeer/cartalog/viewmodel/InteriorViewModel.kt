package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.CarColor
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class InteriorViewModel(private val repository: CarRepository) : ViewModel() {

    private val _colorList = MutableLiveData<List<CarColor>>()
    val colorList: LiveData<List<CarColor>> = _colorList

    private val _selectedColorIdx = MutableLiveData(0)
    val selectedColorIdx: LiveData<Int> = _selectedColorIdx

    private lateinit var selectedByUser: PriceData

    private val _selectedColor = MutableLiveData<CarColor>()
    private val _userTotalPrice = MutableLiveData(0)
    val userTotalPrice: LiveData<Int> = _userTotalPrice

    init {
        setInteriorColorData()
    }

    private fun setInteriorColorData() {
        viewModelScope.launch {
            selectedByUser = repository.getTypeData(PriceDataType.INTERIOR_COLOR)
            val exteriorColor = repository.getTypeData(PriceDataType.EXTERIOR_COLOR)
            _colorList.value = repository.getCarColors(false, 2, exteriorColor.colorCode!!)
            _selectedColorIdx.value = colorList.value?.indices?.find {
                colorList.value?.get(it)?.code == selectedByUser.colorCode
            }
            _selectedColor.value = colorList.value?.get(selectedColorIdx.value!!)
        }
    }

    fun setSelectedColor(selected: Int) {
        _userTotalPrice.value = _userTotalPrice.value?.minus(_selectedColor.value!!.price)
        _selectedColorIdx.value = selected
        _selectedColor.value = colorList.value?.get(selectedColorIdx.value!!)
        _userTotalPrice.value = _userTotalPrice.value?.plus(_selectedColor.value!!.price)
    }

    suspend fun saveUserSelection() {
        val selectedColor = _colorList.value?.get(_selectedColorIdx.value!!)
        val newColor = selectedColor?.let {
            selectedByUser.copy(
                name = it.name, price = it.price, colorCode = it.code, imgUrl = it.colorImageUrl
            )
        }
        repository.saveUserColorData(newColor!!)
    }

    fun setUserTotalPrice(price: Int) {
        _userTotalPrice.value = price
    }
}
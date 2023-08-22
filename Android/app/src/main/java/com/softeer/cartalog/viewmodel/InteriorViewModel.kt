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

    private val _userTotalPrice = MutableLiveData(0)
    val userTotalPrice: LiveData<Int> = _userTotalPrice

    private var selectedByUser: PriceData? = null
    private var selectedColor: CarColor? = null

    init {
        setInteriorColorData()
    }

    private fun setInteriorColorData() {
        viewModelScope.launch {
            selectedByUser = repository.getTypeData(PriceDataType.INTERIOR_COLOR)
            val exteriorColor = repository.getTypeData(PriceDataType.EXTERIOR_COLOR)
            _colorList.value = repository.getCarColors(false, 2, exteriorColor.code!!)
            _selectedColorIdx.value = colorList.value?.indices?.find {
                colorList.value?.get(it)?.code == selectedByUser?.code
            }
            selectedColor = colorList.value?.get(selectedColorIdx.value!!)!!
        }
    }

    fun setSelectedColor(selected: Int) {
        _userTotalPrice.value = _userTotalPrice.value?.minus(selectedColor?.price!!)
        _selectedColorIdx.value = selected
        selectedColor = colorList.value?.get(selectedColorIdx.value!!)!!
        _userTotalPrice.value = _userTotalPrice.value?.plus(selectedColor?.price!!)
    }

    suspend fun saveUserSelection() {
        val newColor = selectedColor?.run {
            selectedByUser?.copy(
                name = name, price = price, code = code, imgUrl = colorImageUrl
            )
        }
        newColor?.let {repository.saveUserColorData(it) }
    }

    fun setUserTotalPrice(price: Int) {
        _userTotalPrice.value = price
    }

    fun updateCarData(){
        viewModelScope.launch {
            val old = repository.getMyCarData()
            val update = old!!.copy(
                interiorImg = selectedColor?.carImageUrl ?: ""
            )
            repository.saveUserCarData(update)
        }
    }
}
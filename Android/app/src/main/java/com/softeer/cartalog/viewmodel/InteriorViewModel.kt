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

    private val _selectedColor = MutableLiveData(0)
    val selectedColor: LiveData<Int> = _selectedColor

    private val _selectedColorByUser = MutableLiveData<PriceData>()

    init {
        setInteriorColorData()
    }

    private fun setInteriorColorData() {
        viewModelScope.launch {
            // TODO : exteriorColorCode 부분 추후 RoomDB에서 불러온값으로 초기화 해야함
            _selectedColorByUser.value = repository.getTypeData(PriceDataType.INTERIOR_COLOR)
            val exteriorColor = repository.getTypeData(PriceDataType.EXTERIOR_COLOR)
            _colorList.value = repository.getCarColors(false, 2, exteriorColor.colorCode!!)
            _selectedColor.value = colorList.value?.indices?.find {
                colorList.value?.get(it)?.code == _selectedColorByUser.value!!.colorCode
            }
        }
    }

    fun setSelectedColor(selectedColor: Int) {
        _selectedColor.value = selectedColor
    }

    suspend fun saveUserSelection() {
        val selectedColor = _colorList.value?.get(_selectedColor.value!!)
        val newColor = selectedColor?.let {
            _selectedColorByUser.value?.copy(
                name = it.name, price = it.price, colorCode = it.code, imgUrl = it.colorImageUrl
            )
        }
        repository.saveUserColorData(newColor!!)
    }
}
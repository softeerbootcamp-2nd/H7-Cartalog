package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.model.CarColor
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class InteriorViewModel(private val repository: CarRepository) : ViewModel() {

    private val _colorList = MutableLiveData<List<CarColor>>()
    val colorList: LiveData<List<CarColor>> = _colorList

    private val _selectedColor = MutableLiveData(0)
    val selectedColor: LiveData<Int> = _selectedColor

    init {
        setInteriorColorData()
    }

    private fun setInteriorColorData() {
        viewModelScope.launch {
            // TODO : exteriorColorCode 부분 추후 RoomDB에서 불러온값으로 초기화 해야함
            _colorList.value = repository.getCarColors(false, 2, "A2B")
        }
    }
}
package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softeer.cartalog.data.model.CarColor
import com.softeer.cartalog.data.repository.CarRepository

class InteriorViewModel(private val repository: CarRepository) : ViewModel() {

    private val _colorList: MutableLiveData<List<CarColor>> by lazy {
        MutableLiveData<List<CarColor>>(setInteriorColorData())
    }
    val colorList: LiveData<List<CarColor>> = _colorList

    private val _selectedColor = MutableLiveData<Int>(0)
    val selectedColor = _selectedColor

    private fun setInteriorColorData(): List<CarColor> {
        // 임시 데이터 설정
        val tmpColor = CarColor("A2B", "어비스 블랙펄", "", "", 0, 38, false)
        return listOf(tmpColor, tmpColor, tmpColor)
    }
}
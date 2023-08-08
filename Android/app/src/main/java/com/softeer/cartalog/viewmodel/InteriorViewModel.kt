package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softeer.cartalog.model.data.CarColor

class InteriorViewModel : ViewModel() {

    private val _colorList: MutableLiveData<List<CarColor>> by lazy {
        MutableLiveData<List<CarColor>>(setInteriorColorata())
    }
    val colorList: LiveData<List<CarColor>> = _colorList

    private val _selectedColor = MutableLiveData<Int>(0)
    val selectedColor = _selectedColor

    private fun setInteriorColorata(): List<CarColor> {
        // 임시 데이터 설정
        val tmpColor = CarColor("A2B", "어비스 블랙펄", "", "", 0, 38)
        return listOf(tmpColor, tmpColor, tmpColor)
    }

    fun changeSelectedTrim(idx: Int) {
        _selectedColor.value = idx
    }
}
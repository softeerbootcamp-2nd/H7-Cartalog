package com.softeer.cartalog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softeer.cartalog.model.data.CarColor

class ExteriorViewModel : ViewModel() {

    private val _colorList = MutableLiveData<List<CarColor>>()

    val colorList: LiveData<List<CarColor>> = _colorList

    private val _selectedColor = MutableLiveData<Int>(0)
    val selectedColor = _selectedColor

    fun setExteriorColorData() {
        // 임시 데이터 설정
        val tmpColor = CarColor("A2B", "어비스 블랙펄", "", "", 0, 38, false)
        _colorList.value = listOf(tmpColor, tmpColor, tmpColor)
    }

}
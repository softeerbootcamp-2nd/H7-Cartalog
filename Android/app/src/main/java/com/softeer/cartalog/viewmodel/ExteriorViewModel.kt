package com.softeer.cartalog.viewmodel

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.model.CarColor
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class ExteriorViewModel(private val repository: CarRepository) : ViewModel() {

    private val _colorList = MutableLiveData<List<CarColor>>()
    val colorList: LiveData<List<CarColor>> = _colorList

    private val _img360List = MutableLiveData<MutableList<Drawable>>()
    val img360List = _img360List

    private val _selectedColor = MutableLiveData(0)
    val selectedColor = _selectedColor

    private val _start360X = MutableLiveData(0f)
    val start360X = _start360X

    init {
        setExteriorColorData()
    }
    private fun setExteriorColorData() {
        viewModelScope.launch {
            _colorList.value = repository.getCarColors(true, 2)
            Log.d("test", _colorList.value.toString())
        }
    }
}
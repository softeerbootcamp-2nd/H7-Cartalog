package com.softeer.cartalog.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.CarColor
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class ExteriorViewModel(private val repository: CarRepository) : ViewModel() {

    private val _colorList = MutableLiveData<List<CarColor>>()
    val colorList: LiveData<List<CarColor>> = _colorList

    private val _img360List = MutableLiveData<MutableList<Drawable>>()
    val img360List: LiveData<MutableList<Drawable>> = _img360List

    private val _selectedColor = MutableLiveData(0)
    val selectedColor: LiveData<Int> = _selectedColor

    private val _start360X = MutableLiveData(0f)
    val start360X: LiveData<Float> = _start360X

    private val _selectedColorByUser = MutableLiveData<PriceData>()

    init {
        setExteriorColorData()
    }

    private fun setExteriorColorData() {
        viewModelScope.launch {
            _colorList.value = repository.getCarColors(true, 2, "")
            _selectedColorByUser.value = repository.getTypeData(PriceDataType.EXTERIOR_COLOR)
            _selectedColor.value = colorList.value?.indices?.find {
                colorList.value?.get(it)?.code == _selectedColorByUser.value!!.colorCode
            }
        }
    }

    fun setSelectedColor(selectedColor: Int) {
        _selectedColor.value = selectedColor
    }

    fun setStart360X(startX: Float) {
        _start360X.value = startX
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
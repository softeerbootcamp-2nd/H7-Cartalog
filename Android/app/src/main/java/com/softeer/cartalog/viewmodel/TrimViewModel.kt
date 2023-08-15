package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.model.Trim
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class TrimViewModel(private val repository: CarRepository) : ViewModel() {

    private val _trimList: MutableLiveData<List<Trim>> = MutableLiveData(emptyList())
    val trimList: LiveData<List<Trim>> = _trimList

    private val _selectedTrim = MutableLiveData<Int>(0)
    val selectedTrim = _selectedTrim

    init {
        setTrimData()
    }

    private fun setTrimData() {
        viewModelScope.launch {
            _trimList.value = repository.getTrims()
        }
    }

    fun changeSelectedTrim(idx: Int) {
        _selectedTrim.value = idx
    }
}
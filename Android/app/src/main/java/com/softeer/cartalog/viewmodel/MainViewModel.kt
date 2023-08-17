package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _stepIndex = MutableLiveData(0)
    val stepIndex: LiveData<Int> = _stepIndex
    private val _budgetRangeLimit = MutableLiveData(50)
    val budgetRangeLimit: LiveData<Int> = _budgetRangeLimit
    private val _totalPrice = MutableLiveData(30)
    val totalPrice: LiveData<Int> = _totalPrice
    private val _isExcess = MutableLiveData(false)
    val isExcess: LiveData<Boolean> = _isExcess

    fun setStepIndex(index: Int) {
        _stepIndex.value = index
    }
}
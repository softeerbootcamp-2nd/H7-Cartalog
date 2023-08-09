package com.softeer.cartalog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _stepIndex = MutableLiveData<Int>(0)
    val stepIndex = _stepIndex
    private val _budgetRangeLimit = MutableLiveData<Int>(50)
    val budgetRangeLimit = _budgetRangeLimit
    private val _totalPrice = MutableLiveData<Int>(30)
    val totalPrice = _totalPrice
    private val _isExcess = MutableLiveData<Boolean>(false)
    val isExcess = _isExcess

    fun setStepIndex(index: Int) {
        _stepIndex.value = index
    }
}
package com.softeer.cartalog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _stepIndex = MutableLiveData<Int>(0)
    val stepIndex = _stepIndex

    fun setStepIndex(index: Int){
        _stepIndex.value = index
    }


}
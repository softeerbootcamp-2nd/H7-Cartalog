package com.softeer.cartalog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _stepIndex = MutableLiveData(0)
    val stepIndex: LiveData<Int> = _stepIndex
    private val _budgetRangeLimit = MutableLiveData(0)
    val budgetRangeLimit: LiveData<Int> = _budgetRangeLimit
    private val _budgetRangeLimitProgress = MutableLiveData(50)
    val budgetRangeLimitProgress: LiveData<Int> = _budgetRangeLimitProgress
    private val _totalPrice = MutableLiveData(0)
    val totalPrice: LiveData<Int> = _totalPrice
    private val _totalPriceProgress = MutableLiveData<Int>(0)
    val totalPriceProgress: LiveData<Int> = _totalPriceProgress
    private val _isExcess = MutableLiveData(false)
    val isExcess: LiveData<Boolean> = _isExcess

    private val _minPrice = MutableLiveData(0)
    val minPrice: LiveData<Int> = _minPrice
    private val _maxPrice = MutableLiveData(0)
    val maxPrice: LiveData<Int> = _maxPrice
    private val _priceRange = MutableLiveData(0)

    fun setStepIndex(index: Int) {
        _stepIndex.value = index
    }

    fun setRangeLimit(progress: Int) {
        _budgetRangeLimitProgress.value = progress
        _budgetRangeLimit.value = calculatePriceFromProgress(progress)
        Log.d("PRICE","budget progress: $progress, price : ${_budgetRangeLimit.value}")
        _isExcess.value = budgetRangeLimit.value!! < totalPrice.value!!
    }

    fun setMinMaxPrice(minPrice: Int, maxPrice: Int){
        _minPrice.value = minPrice
        _maxPrice.value = maxPrice
        _priceRange.value = maxPrice - minPrice
    }

    fun setTotalPriceProgress(total : Int){
        _totalPrice.value = total
        _totalPriceProgress.value = calculateProgressFromPrice(totalPrice.value!!)
        _budgetRangeLimit.value = calculatePriceFromProgress(budgetRangeLimitProgress.value!!)
        Log.d("PRICE","total price = ${_totalPrice.value}")
        Log.d("PRICE","total price progress = ${_totalPriceProgress.value}")
        Log.d("PRICE","init budget progress: ${budgetRangeLimitProgress.value}, price : ${_budgetRangeLimit.value}")
    }

    private fun calculateProgressFromPrice(price: Int): Int {

        Log.d("PRICE","total price range = ${_priceRange.value}")
        val adjustedValue = price - minPrice.value!!
        return (adjustedValue.toFloat() / _priceRange.value!!.toFloat() * 100).toInt()
    }

    private fun calculatePriceFromProgress(progress: Int): Int {
        return minPrice.value!! + (_priceRange.value!! * progress / 100)
    }
}
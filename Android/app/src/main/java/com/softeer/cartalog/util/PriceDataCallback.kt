package com.softeer.cartalog.util

interface PriceDataCallback {
    fun onInitPriceDataReceived(priceList: List<Int>)
    fun changeUserTotalPrice(price: Int)
}
package com.softeer.cartalog.data.repository.local

import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.model.db.MyCar
import com.softeer.cartalog.data.model.db.PriceData

class CarLocalDataSource(
    private val myCarDb: MyCarDatabase
) {
    private val myCarDao = myCarDb.myCarDao()
    private val priceDataDao = myCarDb.priceDataDao()

    suspend fun setInitialMyCar(input: MyCar): Int {
        return myCarDao.insertMyCar(input).toInt()
    }

    suspend fun setInitialPriceData(inputs: List<PriceData>) {
        priceDataDao.insertPriceDataList(inputs)
    }

    suspend fun isEmpty(carId: Int): Boolean {
        return priceDataDao.getPriceDataCountByCarId(carId) == 0
    }
}
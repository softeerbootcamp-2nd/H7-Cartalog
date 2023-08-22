package com.softeer.cartalog.data.repository.local

import com.softeer.cartalog.data.enums.PriceDataType
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

    suspend fun getMyCar(): MyCar? {
        return myCarDao.getMyCar()
    }

    suspend fun getPriceDataList(): List<PriceData> {
        return priceDataDao.getPriceDataList()
    }

    suspend fun getPriceData(type: PriceDataType): PriceData {
        return priceDataDao.getTypeData(type)
    }

    suspend fun updatePriceData(input: PriceData) {
        priceDataDao.insertPriceData(input)
    }

    suspend fun updateMyCarData(input: MyCar) {
        myCarDao.upDateMyCar(input)
    }

    suspend fun getOptionTypeDataList(): List<PriceData> {
        return priceDataDao.getTypeDataList(PriceDataType.OPTION)
    }

    suspend fun insertOptionDataList(optionList: List<PriceData>): List<Long> {
        return priceDataDao.insertOptionDataList(optionList)
    }

    suspend fun deleteOptionItem(option: PriceData){
        priceDataDao.deleteOptionItem(option)
    }
}
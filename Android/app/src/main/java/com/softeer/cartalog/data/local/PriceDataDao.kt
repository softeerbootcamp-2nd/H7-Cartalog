package com.softeer.cartalog.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.db.PriceData

@Dao
interface PriceDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceDataList(priceDataList: List<PriceData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceData(priceData: PriceData)

    @Query("SELECT * FROM PriceData WHERE PriceData.optionType = :type")
    suspend fun getTypeData(type: PriceDataType): PriceData

    @Query("SELECT COUNT(*) FROM PriceData WHERE carId = :carId")
    suspend fun getPriceDataCountByCarId(carId: Int): Int
}
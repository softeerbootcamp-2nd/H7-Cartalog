package com.softeer.cartalog.data.local

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("SELECT * FROM PriceData")
    suspend fun getPriceDataList(): List<PriceData>

    @Query("SELECT * FROM PriceData WHERE PriceData.optionType = :type")
    suspend fun getTypeDataList(type: PriceDataType): List<PriceData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptionDataList(priceDataList: List<PriceData>): List<Long>

    @Delete
    suspend fun deleteOptionItem(option: PriceData)
}
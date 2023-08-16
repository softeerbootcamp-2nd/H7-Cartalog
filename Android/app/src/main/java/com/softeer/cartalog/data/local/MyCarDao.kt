package com.softeer.cartalog.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.softeer.cartalog.data.model.MyCar
import com.softeer.cartalog.data.model.MyCarWithPriceData

@Dao
interface MyCarDao {

    @Insert
    suspend fun insertMyCar(myCar: MyCar): Long

    @Transaction
    @Query("SELECT * FROM MyCar")
    suspend fun getAll(): MyCarWithPriceData
}
package com.softeer.cartalog.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.softeer.cartalog.data.model.db.MyCar
import com.softeer.cartalog.data.model.db.MyCarWithPriceData

@Dao
interface MyCarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyCar(myCar: MyCar): Long

    @Transaction
    @Query("SELECT * FROM MyCar")
    suspend fun getAll(): MyCarWithPriceData

    @Query("SELECT * FROM MyCar")
    suspend fun getMyCar(): MyCar?

    @Update
    suspend fun upDateMyCar(myCar: MyCar)

    @Query("DELETE FROM MyCar")
    suspend fun deleteAll()
}
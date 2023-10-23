package com.demo.med.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.med.database.entites.DbHealthData

@Dao
abstract class HealthDao {
    @Query("SELECT * FROM db_healthdata")
    abstract suspend fun getHealthData(): DbHealthData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveHealthData(healthData: DbHealthData)

}
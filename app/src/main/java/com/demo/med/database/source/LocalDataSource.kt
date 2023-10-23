package com.demo.med.database.source

import com.demo.med.database.entites.HealthData

interface LocalDataSource {
    suspend fun geHealthData(): MutableList<HealthData>?
    suspend fun saveHealthData(healthList: MutableList<HealthData>)
}
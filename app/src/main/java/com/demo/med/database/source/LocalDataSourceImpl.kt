package com.demo.med.database.source

import com.demo.med.database.dao.HealthDao
import com.demo.med.database.entites.HealthData
import com.demo.med.database.mapper.HealthDataMapper
import com.demo.med.di.qualifiers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LocalDataSourceImpl @Inject constructor(
    private val dao: HealthDao,
    private val healthMapper: HealthDataMapper,
    @IO private val context: CoroutineContext
) : LocalDataSource {
    override suspend fun geHealthData() = withContext(context) {
        val healthEntity = dao.getHealthData()
        if (healthEntity != null)
            healthMapper.to(healthEntity)
        else
            null
    }

    override suspend fun saveHealthData(healthList: MutableList<HealthData>) =
        withContext(context) {
            val health = healthMapper.from(healthList)
            dao.saveHealthData(health)
        }

}
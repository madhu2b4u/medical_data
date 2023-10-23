package com.demo.med.database.mapper

import com.demo.med.database.entites.DbHealthData
import com.demo.med.database.entites.HealthData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class HealthDataMapper @Inject constructor(val gson: Gson) :
    Mapper<DbHealthData, MutableList<HealthData>> {
    override fun from(e: MutableList<HealthData>) = DbHealthData(1, gson.toJson(e))
    override fun to(t: DbHealthData): MutableList<HealthData> {
        return gson.fromJson(
            t.news,
            TypeToken.getParameterized(MutableList::class.java, HealthData::class.java).type
        )
    }
}
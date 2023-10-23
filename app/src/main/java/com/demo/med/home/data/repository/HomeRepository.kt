package com.demo.med.home.data.repository

import androidx.lifecycle.LiveData
import com.demo.med.common.Result
import com.demo.med.database.entites.HealthData


interface HomeRepository {
    suspend fun drugsRequest(isLoaded: Boolean): LiveData<Result<MutableList<HealthData>>>

}
package com.demo.med.home.domain

import androidx.lifecycle.LiveData
import com.demo.med.common.Result
import com.demo.med.database.entites.HealthData

interface HomeUseCase {
    suspend fun drugsRequest(isLoaded: Boolean): LiveData<Result<MutableList<HealthData>>>

}
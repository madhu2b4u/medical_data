package com.demo.med.home.data.remote.source

import com.demo.med.di.qualifiers.IO
import com.demo.med.home.data.remote.services.HomeService
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class HomeRemoteDataSourceImpl @Inject constructor(
    private val service: HomeService,
    @IO private val context: CoroutineContext
) : HomeRemoteDataSource {

    override suspend fun drugsRequest() = withContext(context) {
        val response = service.requestDrugsAsync().await()
        if (response.isSuccessful)
            response.body() ?: throw Exception("no list")
        else
            throw Exception("no drugs")
    }
}

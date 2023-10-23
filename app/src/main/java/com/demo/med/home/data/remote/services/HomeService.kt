package com.demo.med.home.data.remote.services

import com.demo.med.home.data.models.DrugsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {

    // id to fetch from the mock apis
    @GET("f7334147-a797-44dd-bf47-70b2e965f765")
    fun requestDrugsAsync(): Deferred<Response<DrugsResponse>>
}
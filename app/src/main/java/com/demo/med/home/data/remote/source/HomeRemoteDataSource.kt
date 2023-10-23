package com.demo.med.home.data.remote.source

import com.demo.med.home.data.models.DrugsResponse


interface HomeRemoteDataSource {
    suspend fun drugsRequest(): DrugsResponse
}
package com.demo.med.home.domain

import com.demo.med.home.data.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeUseCaseImpl @Inject constructor(private val mHomeRepository: HomeRepository) :
    HomeUseCase {
    override suspend fun drugsRequest(isLoaded: Boolean) = mHomeRepository.drugsRequest(isLoaded)
}

package com.demo.med.home.di

import com.demo.med.home.data.repository.HomeRepository
import com.demo.med.home.data.repository.HomeRepositoryImpl
import com.demo.med.home.domain.HomeUseCase
import com.demo.med.home.domain.HomeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeDomainModule {

    @Binds
    abstract fun bindsRepository(
        repoImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    abstract fun bindsHomeUseCase(
        mHomeUseCase: HomeUseCaseImpl
    ): HomeUseCase
}
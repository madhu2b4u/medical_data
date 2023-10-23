package com.demo.med.home.di


import com.demo.med.home.data.remote.services.HomeService
import com.demo.med.home.data.remote.source.HomeRemoteDataSource
import com.demo.med.home.data.remote.source.HomeRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module(includes = [HomeRemoteModule.Binders::class])
@InstallIn(SingletonComponent::class)
class HomeRemoteModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: HomeRemoteDataSourceImpl
        ): HomeRemoteDataSource
    }

    @Provides
    fun providesHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)
}
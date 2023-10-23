package com.demo.med.di

import android.app.Application
import com.demo.med.database.dao.HealthDatabase
import com.demo.med.database.source.LocalDataSource
import com.demo.med.database.source.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [HealthLocalModule.Binders::class])
@InstallIn(SingletonComponent::class)
class HealthLocalModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsLocalDataSource(
            localDataSourceImpl: LocalDataSourceImpl
        ): LocalDataSource
    }

    @Provides
    @Singleton
    fun providesDatabase(
        application: Application
    ) = HealthDatabase.getInstance(application.applicationContext)

    @Provides
    @Singleton
    fun providesUserDao(
        data: HealthDatabase
    ) = data.getHealthDao()
}
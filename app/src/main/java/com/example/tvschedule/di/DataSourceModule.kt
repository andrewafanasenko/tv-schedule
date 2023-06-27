package com.example.tvschedule.di

import com.example.tvschedule.data.schedule.source.local.ScheduleLocalDataSource
import com.example.tvschedule.data.schedule.source.local.ScheduleLocalDataSourceImpl
import com.example.tvschedule.data.schedule.source.remote.ScheduleRemoteDataSource
import com.example.tvschedule.data.schedule.source.remote.ScheduleRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideScheduleRemoteDataSource(
        scheduleRemoteDataSource: ScheduleRemoteDataSourceImpl
    ): ScheduleRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideScheduleLocalDataSource(
        scheduleLocalDataSource: ScheduleLocalDataSourceImpl
    ): ScheduleLocalDataSource

}

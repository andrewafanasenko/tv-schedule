package com.example.tvschedule.di

import com.example.tvschedule.data.favorite.source.local.FavoriteLocalDataSource
import com.example.tvschedule.data.favorite.source.local.FavoriteLocalDataSourceImpl
import com.example.tvschedule.data.schedule.source.local.ScheduleLocalDataSource
import com.example.tvschedule.data.schedule.source.local.ScheduleLocalDataSourceImpl
import com.example.tvschedule.data.schedule.source.remote.ScheduleRemoteDataSource
import com.example.tvschedule.data.schedule.source.remote.ScheduleRemoteDataSourceImpl
import com.example.tvschedule.data.search.source.local.SearchLocalDataSource
import com.example.tvschedule.data.search.source.local.SearchLocalDataSourceImpl
import com.example.tvschedule.data.search.source.remote.SearchRemoteDataSource
import com.example.tvschedule.data.search.source.remote.SearchRemoteDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun provideSearchRemoteDataSource(
        searchRemoteDataSource: SearchRemoteDataSourceImpl
    ): SearchRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideSearchLocalDataSource(
        searchLocalDataSource: SearchLocalDataSourceImpl
    ): SearchLocalDataSource

    @Binds
    @Singleton
    abstract fun provideFavoriteLocalDataSource(
        searchLocalDataSource: FavoriteLocalDataSourceImpl
    ): FavoriteLocalDataSource

}

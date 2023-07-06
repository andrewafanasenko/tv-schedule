package com.example.tvschedule.di

import com.example.tvschedule.data.favorite.repository.FavoriteRepositoryImpl
import com.example.tvschedule.data.schedule.repository.ScheduleRepositoryImpl
import com.example.tvschedule.data.search.repository.SearchRepositoryImpl
import com.example.tvschedule.data.show_details.repository.ShowDetailsRepositoryImpl
import com.example.tvschedule.domain.favorite.repository.FavoriteRepository
import com.example.tvschedule.domain.schedule.repository.ScheduleRepository
import com.example.tvschedule.domain.search.repository.SearchRepository
import com.example.tvschedule.domain.show_details.repository.ShowDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideScheduleRepository(
        scheduleRepository: ScheduleRepositoryImpl
    ): ScheduleRepository

    @Binds
    @Singleton
    abstract fun provideSearchRepository(
        searchRepository: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun provideFavoriteRepository(
        favoriteRepository: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Binds
    @Singleton
    abstract fun provideShowDetailsRepository(
        showDetailsRepository: ShowDetailsRepositoryImpl
    ): ShowDetailsRepository

}


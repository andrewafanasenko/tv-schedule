package com.example.tvschedule.di

import com.example.tvschedule.data.schedule.repository.ScheduleRepositoryImpl
import com.example.tvschedule.domain.schedule.repository.ScheduleRepository
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

}


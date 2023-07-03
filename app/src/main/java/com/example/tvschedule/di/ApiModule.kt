package com.example.tvschedule.di

import com.example.tvschedule.data.schedule.api.ScheduleApi
import com.example.tvschedule.data.search.api.SearchApi
import com.example.tvschedule.data.show_details.api.ShowDetailsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideScheduleApi(
        retrofit: Retrofit
    ): ScheduleApi = retrofit.create(ScheduleApi::class.java)

    @Provides
    @Singleton
    fun provideSearchApi(
        retrofit: Retrofit
    ): SearchApi = retrofit.create(SearchApi::class.java)

    @Provides
    @Singleton
    fun provideShowDetailsApi(
        retrofit: Retrofit
    ): ShowDetailsApi = retrofit.create(ShowDetailsApi::class.java)

}

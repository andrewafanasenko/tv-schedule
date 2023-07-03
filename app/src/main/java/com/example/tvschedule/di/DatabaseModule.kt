package com.example.tvschedule.di

import android.content.Context
import androidx.room.Room
import com.example.tvschedule.data.db.MIGRATION_1_2
import com.example.tvschedule.data.db.MIGRATION_2_3
import com.example.tvschedule.data.db.ShowsDatabase
import com.example.tvschedule.data.favorite.source.local.db.FavoriteShowsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ShowsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ShowsDatabase::class.java,
            "Shows.db"
        )
            .fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    @Provides
    fun provideFavoriteShowsDao(
        database: ShowsDatabase
    ): FavoriteShowsDao = database.favoriteShowsDao()
}

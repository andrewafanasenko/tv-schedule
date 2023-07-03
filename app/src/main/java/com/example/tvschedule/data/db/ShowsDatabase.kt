package com.example.tvschedule.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tvschedule.data.favorite.source.local.db.FavoriteShowsDao
import com.example.tvschedule.data.favorite.source.local.db.model.ShowEntity

@Database(
    entities = [ShowEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ShowsDatabase : RoomDatabase() {

    abstract fun favoriteShowsDao(): FavoriteShowsDao
}

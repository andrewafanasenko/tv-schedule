package com.example.tvschedule.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tvschedule.data.favorite.source.local.db.FavoriteShowsDao
import com.example.tvschedule.data.favorite.source.local.db.model.ShowEntity

@Database(
    entities = [ShowEntity::class],
    version = 3,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class ShowsDatabase : RoomDatabase() {

    abstract fun favoriteShowsDao(): FavoriteShowsDao
}

package com.example.tvschedule.data.favorite.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tvschedule.data.favorite.source.local.db.model.ShowEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShow(show: ShowEntity)

    @Query("DELETE FROM show WHERE show.id is :showId")
    suspend fun deleteShow(showId: Long): Int

    @Update
    suspend fun updateShow(show: ShowEntity)

    @Query("SELECT * FROM show")
    fun showsUpdates(): Flow<List<ShowEntity>>

    @Query("SELECT * FROM show WHERE id is :showId")
    suspend fun getShow(showId: Long): ShowEntity?
}

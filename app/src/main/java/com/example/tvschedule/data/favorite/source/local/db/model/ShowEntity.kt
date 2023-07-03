package com.example.tvschedule.data.favorite.source.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "show")
data class ShowEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "summary") val summary: String,
    @ColumnInfo(name = "coverUrl") val coverUrl: String
)

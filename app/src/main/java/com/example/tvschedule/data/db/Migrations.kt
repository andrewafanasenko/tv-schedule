package com.example.tvschedule.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE show ADD COLUMN rating DOUBLE NULL DEFAULT NULL")
        db.execSQL("ALTER TABLE show ADD COLUMN averageRuntime INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE show ADD COLUMN genres TEXT NOT NULL DEFAULT ''")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE show ADD COLUMN originalCoverUrl TEXT NOT NULL DEFAULT ''")
    }
}

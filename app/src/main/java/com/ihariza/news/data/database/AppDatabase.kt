package com.ihariza.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ihariza.news.data.database.converters.CategoryListConverter
import com.ihariza.news.data.entity.ReportEntity

@Database(entities = [ReportEntity::class], version = 1, exportSchema = false)
@TypeConverters(CategoryListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao

    companion object {
        const val DATABASE_NAME = "app-db"
    }
}
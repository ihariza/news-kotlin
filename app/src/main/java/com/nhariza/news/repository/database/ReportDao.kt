package com.nhariza.news.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nhariza.news.repository.database.entity.ReportEntity


@Dao
interface ReportDao {
    @Query("SELECT * FROM report where page LIKE :pageNumber")
    suspend fun getAll(pageNumber: Int): List<ReportEntity>?

    @Query("SELECT * FROM report where id LIKE :id")
    suspend fun findBy(id: String?): ReportEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(report: ReportEntity)

    @Query("DELETE FROM report")
    suspend fun deleteAll()
}
package com.ihariza.news.data.database

import androidx.room.*
import com.ihariza.news.data.entity.ReportEntity


@Dao
interface ReportDao {
    @Query("SELECT * FROM report where page LIKE :pageNumber")
    suspend fun getAll(pageNumber: Int): List<ReportEntity>?

    @Query("SELECT * FROM report where id LIKE :id")
    suspend fun findBy(id: String?): ReportEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(report: ReportEntity)

    @Query("DELETE FROM report")
    suspend fun deleteAll()
}
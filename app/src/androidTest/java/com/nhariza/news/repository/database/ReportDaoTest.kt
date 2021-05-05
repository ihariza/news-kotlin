package com.nhariza.news.repository.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nhariza.news.builder.ReportDtoBuilder
import com.nhariza.news.repository.datasource.model.toEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ReportDaoTest {

    private lateinit var reportDao: ReportDao
    private lateinit var db: AppDatabase
    private val report = ReportDtoBuilder.build().toEntity()


    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java).build()
        reportDao = db.reportDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertReportAndFindInDb() = runBlocking {
        reportDao.insert(report)
        assertEquals(report, reportDao.findBy(report.id))
    }

    @Test
    fun insertReportsAndDeleteAll() = runBlocking {
        reportDao.insert(report)
        reportDao.insert(report.copy(id = "88ae9d5a-cyc0-4f3e-8dbf-93b4781d1a99"))
        reportDao.deleteAll()
        assertTrue(reportDao.getAll(0)!!.isEmpty())
    }
}
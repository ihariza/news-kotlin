package com.ihariza.news.data.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.ihariza.news.fake.FakeNewsAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ReportDatabaseTest {

    private lateinit var database: AppDatabase
    private lateinit var reportDao: ReportDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
                AppDatabase::class.java).build()
        reportDao = database.reportDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    @Throws(IOException::class)
    fun writeReportAndReadInList() = runBlocking {
        reportDao.insert(FakeNewsAndroidTest.getReportEntity())

        assert(reportDao.getAll(FakeNewsAndroidTest.PAGE_ONE)?.get(0)
                == FakeNewsAndroidTest.getReportEntity())
    }

    @Test
    @Throws(IOException::class)
    fun writeReportAndGetById() = runBlocking {
        val report = FakeNewsAndroidTest.getReportEntity()
        reportDao.insert(report)

        assert(reportDao.findBy(report.id) == report)
    }

    @Test
    @Throws(IOException::class)
    fun writeReportAndDeleteAll() = runBlocking {
        reportDao.insert(FakeNewsAndroidTest.getReportEntity())

        assert(reportDao.getAll(FakeNewsAndroidTest.PAGE_ONE)?.count() == 0)
    }

}
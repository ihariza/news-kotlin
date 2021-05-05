package com.nhariza.news.injection

import androidx.room.Room
import com.nhariza.news.repository.NewsRepository
import com.nhariza.news.repository.NewsRepositoryImp
import com.nhariza.news.repository.database.AppDatabase
import com.nhariza.news.repository.database.NewsLocalDataSource
import com.nhariza.news.repository.datasource.NewsRemoteDataSource
import org.koin.dsl.module


val repositoryModule = module {

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()
    }

    single { get<AppDatabase>().reportDao() }

    single { NewsLocalDataSource(get()) }

    single { NewsRemoteDataSource(get()) }

    single<NewsRepository> {
        NewsRepositoryImp(
            newsLocalDataSource = get(),
            newsRemoteDataSource = get()
        )
    }
}
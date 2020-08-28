package com.ihariza.news.injection

import androidx.room.Room
import com.ihariza.news.data.database.AppDatabase
import com.ihariza.news.data.repository.NewsRepositoryImp
import com.ihariza.news.data.repository.local.LocalNewsRepository
import com.ihariza.news.data.repository.local.LocalNewsRepositoryImp
import com.ihariza.news.data.repository.remote.RemoteNewsRepository
import com.ihariza.news.data.repository.remote.RemoteNewsRepositoryImp
import com.ihariza.news.domain.repository.NewsRepository
import org.koin.dsl.module


val repositoryModule = module {

    single { Room.databaseBuilder(get(), AppDatabase::class.java, AppDatabase.DATABASE_NAME).build() }

    single { get<AppDatabase>().reportDao() }

    single<LocalNewsRepository> { LocalNewsRepositoryImp(get())  }

    single<RemoteNewsRepository> { RemoteNewsRepositoryImp(get())  }

    single<NewsRepository> { NewsRepositoryImp(
            localNewsRepository = get(),
            remoteNewsRepository = get())
    }
}
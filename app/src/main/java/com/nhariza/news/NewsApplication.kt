package com.nhariza.news

import android.app.Application
import com.nhariza.news.injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupInjection()
    }

    private fun setupInjection() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@NewsApplication)
            modules(
                listOf(
                    managerModule,
                    networkModule,
                    repositoryModule,
                    newsModule,
                    reportModule
                )
            )
        }
    }
}
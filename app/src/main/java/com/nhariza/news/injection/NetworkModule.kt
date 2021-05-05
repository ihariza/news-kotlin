package com.nhariza.news.injection

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.nhariza.news.EnvironmentConfig
import com.nhariza.news.FlavorEnvironmentConfig
import com.nhariza.news.repository.datasource.NewsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val environmentConfig: EnvironmentConfig = FlavorEnvironmentConfig()

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

private val okHttpClient = OkHttpClient.Builder().apply {
    addInterceptor(loggingInterceptor)
    connectTimeout(10, TimeUnit.SECONDS)
    readTimeout(110, TimeUnit.SECONDS)
}.build()

private val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

private val retrofit = Retrofit.Builder().client(okHttpClient).baseUrl(environmentConfig.baseUrl)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

private val newsService: NewsService = retrofit.create(NewsService::class.java)

val networkModule = module {
    single { loggingInterceptor }
    single { okHttpClient }
    single { gson }
    single { retrofit }
    single { newsService }
}
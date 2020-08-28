package com.ihariza.news.injection

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.ihariza.news.BuildConfig
import com.ihariza.news.data.api.NewsApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private val okHttpClient = OkHttpClient.Builder().apply {
    connectTimeout(10, TimeUnit.SECONDS)
    readTimeout(110, TimeUnit.SECONDS)
}.build()

private val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

private val retrofit = Retrofit.Builder().client(okHttpClient).baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

private val newsApi: NewsApi = retrofit.create(NewsApi::class.java)

val networkModule = module {
    single { newsApi }
}
package com.ihariza.news.injection

import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val managerModule = module {
    single { Glide.with(androidContext()) }
}
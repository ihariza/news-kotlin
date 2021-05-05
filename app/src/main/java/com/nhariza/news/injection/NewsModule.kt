package com.nhariza.news.injection

import com.nhariza.news.view.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    viewModel { NewsViewModel(newsRepository = get()) }
}
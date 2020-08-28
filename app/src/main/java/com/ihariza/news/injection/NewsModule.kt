package com.ihariza.news.injection

import com.ihariza.news.domain.usecase.GetNewsUseCase
import com.ihariza.news.presentation.view.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    factory { GetNewsUseCase(newsRepository = get()) }
    viewModel { NewsViewModel(getNewsUseCase = get()) }
}
package com.nhariza.news.injection

import com.nhariza.news.view.report.ReportViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val reportModule = module {
    viewModel { ReportViewModel(newsRepository = get()) }
}
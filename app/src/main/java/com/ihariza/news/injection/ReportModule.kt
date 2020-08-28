package com.ihariza.news.injection

import com.ihariza.news.domain.usecase.GetReportUseCase
import com.ihariza.news.presentation.view.report.ReportViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val reportModule = module {
    factory { GetReportUseCase(newsRepository = get()) }
    viewModel { ReportViewModel(getReportUseCase = get()) }
}
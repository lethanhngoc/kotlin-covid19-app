package com.example.kotlin_covid19_app.di

import com.example.kotlin_covid19_app.ui.dailygraph.DailyGraphViewModel
import com.example.kotlin_covid19_app.ui.dashboard.DashboardViewModel
import com.example.kotlin_covid19_app.ui.detail.DetailViewModel
import com.example.kotlin_covid19_app.widget.LocationWidgetViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { DailyGraphViewModel(get(), get()) }
//    viewModel { CountryIndonesiaViewModel(get(), get()) }
    viewModel { LocationWidgetViewModel(get(), get()) }
}
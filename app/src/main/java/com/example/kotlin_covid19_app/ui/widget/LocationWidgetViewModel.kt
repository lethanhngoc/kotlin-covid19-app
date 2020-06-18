package com.example.kotlin_covid19_app.widget

import com.example.kotlin_covid19_app.data.repository.Repository
import com.example.kotlin_covid19_app.ui.base.BaseViewModel
import com.example.kotlin_covid19_app.util.rx.SchedulerProvider


data class LocationWidgetViewModel(
    private val appRepository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

}
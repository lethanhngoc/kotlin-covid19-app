package com.example.kotlin_covid19_app.di

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.util.rx.AppSchedulerProvider
import com.example.kotlin_covid19_app.util.rx.SchedulerProvider
import org.koin.dsl.module
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

const val DEFAULT_FONT = "fonts/GoogleSans-Regular.ttf"

val appModule = module {

    single {
        CalligraphyConfig.Builder()
            .setDefaultFontPath(DEFAULT_FONT)
            .setFontAttrId(R.attr.fontPath)
            .build()
    }

    factory<SchedulerProvider> {
        AppSchedulerProvider()
    }


    factory {
        LinearLayoutManager(get())
    }

}
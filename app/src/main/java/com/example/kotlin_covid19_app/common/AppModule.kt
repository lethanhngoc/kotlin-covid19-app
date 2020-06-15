package com.example.kotlin_covid19_app.common

import com.example.kotlin_covid19_app.R
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import org.koin.dsl.module

const val DEFAULT_FONT = "fonts/GoogleSans-Regular.ttf"


val appModule = module {

    single {
        CalligraphyConfig.Builder()
            .setDefaultFontPath(DEFAULT_FONT)
            .setFontAttrId(R.attr.fontPath)
            .build()
    }
}
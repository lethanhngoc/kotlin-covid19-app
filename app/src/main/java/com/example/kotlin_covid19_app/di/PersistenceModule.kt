package com.example.kotlin_covid19_app.di

import com.example.kotlin_covid19_app.data.source.generated.AppGeneratedSource
import com.example.kotlin_covid19_app.data.source.pref.AppPrefSource
import io.reactivex.schedulers.Schedulers.single
import org.koin.dsl.module

val persistenceModule = module {
    single {
        AppPrefSource()
    }

    single {
        AppGeneratedSource()
    }
}


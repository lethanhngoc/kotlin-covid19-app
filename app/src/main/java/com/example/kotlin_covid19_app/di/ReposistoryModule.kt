package com.example.kotlin_covid19_app.di

import com.example.kotlin_covid19_app.data.repository.AppRepository
import com.example.kotlin_covid19_app.data.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    factory<Repository> {
        AppRepository(get(), get(), get())
    }
}
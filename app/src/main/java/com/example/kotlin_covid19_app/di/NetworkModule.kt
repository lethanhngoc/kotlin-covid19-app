package com.example.kotlin_covid19_app.di

import com.example.kotlin_covid19_app.BuildConfig
import com.example.kotlin_covid19_app.data.source.remote.Api
import com.example.kotlin_covid19_app.data.source.remote.AppRemoteSource
import com.example.kotlin_covid19_app.util.Constant
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    val BASEURL = "https://covid19.mathdro.id/"
    single {
        OkHttpClient().newBuilder()
            .connectTimeout(Constant.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constant.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constant.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    single {
        AppRemoteSource(get())
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(get<OkHttpClient>())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(Api::class.java)
    }


}
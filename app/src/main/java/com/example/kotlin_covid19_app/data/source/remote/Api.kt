package com.example.kotlin_covid19_app.data.source.remote

import com.example.kotlin_covid19_app.data.model.CovidDaily
import com.example.kotlin_covid19_app.data.model.CovidDetail
import com.example.kotlin_covid19_app.data.model.CovidOverview
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

@JvmSuppressWildcards
interface Api {
    @GET("api")
    fun overview(): Observable<CovidOverview>

    @GET("api/daily")
    fun daily(): Observable<List<CovidDaily>>

    @GET("api/confirmed")
    fun confirmed(): Observable<List<CovidDetail>>

    @GET("api/deaths")
    fun deaths(): Observable<List<CovidDetail>>

    @GET("api/recovered")
    fun recovered(): Observable<List<CovidDetail>>

    @GET("api/countries/{country}")
    fun country(@Path("country") country: String): Observable<CovidOverview>



}
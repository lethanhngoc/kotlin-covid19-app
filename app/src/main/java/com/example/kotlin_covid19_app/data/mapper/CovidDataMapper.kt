package com.example.kotlin_covid19_app.data.mapper

import com.example.kotlin_covid19_app.data.model.CovidDaily
import com.example.kotlin_covid19_app.data.model.CovidDetail
import com.example.kotlin_covid19_app.data.model.CovidOverview
import com.example.kotlin_covid19_app.ui.adapter.viewholder.DailyItem
import com.example.kotlin_covid19_app.ui.adapter.viewholder.LocationItem
import com.example.kotlin_covid19_app.ui.adapter.viewholder.OverviewItem
import com.example.kotlin_covid19_app.ui.adapter.viewholder.PinnedItem
import com.example.kotlin_covid19_app.util.CaseTypes

object CovidDailyDataMapper {

    fun transform(responses: List<CovidDaily>?) = responses?.map { response ->
        DailyItem(
            response.deltaConfirmed,
            response.deltaRecovered,
            response.mainlandChina,
            response.otherLocations,
            response.reportDate,
            response.incrementRecovered,
            response.incrementConfirmed
        )
    }.orEmpty()
}

object CovidOverviewDataMapper {

    fun transform(response: CovidOverview?) = OverviewItem(
        response?.confirmed?.value ?: 0,
        response?.recovered?.value ?: 0,
        response?.deaths?.value ?: 0)
}

object CovidPinnedDataMapper {

    fun transform(response: CovidDetail?) : PinnedItem? = if(response != null) PinnedItem(
        response.confirmed,
        response.recovered,
        response.deaths,
        response.locationName,
        response.lastUpdate)
    else null
}

object CovidDetailDataMapper {

    fun transform(responses: List<CovidDetail>?, @CaseTypes caseType: Int) = responses?.map { response ->
        LocationItem(
            response.confirmed,
            response.recovered,
            response.deaths,
            response.locationName,
            response.lastUpdate,
            response.lat,
            response.long,
            response.countryRegion,
            response.provinceState,
            caseType
        )
    }.orEmpty()
}

object CovidDataMapper{

    fun transformOverviewToUpdatedRegion(covidDetail: CovidDetail, covidOverview: CovidOverview) =  CovidDetail(
        confirmed = covidOverview.confirmed?.value ?: 0,
        deaths = covidOverview.deaths?.value ?: 0,
        recovered = covidOverview.recovered?.value ?: 0,
        countryRegion = covidDetail.countryRegion,
        lastUpdate = covidDetail.lastUpdate,
        lat = covidDetail.lat,
        long = covidDetail.long,
        iso2 = covidDetail.iso2,
        provinceState = covidDetail.provinceState
    )
}
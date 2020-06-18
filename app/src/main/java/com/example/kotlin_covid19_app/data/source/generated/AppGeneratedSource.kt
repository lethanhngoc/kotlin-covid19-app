package com.example.kotlin_covid19_app.data.source.generated

import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.ui.adapter.viewholder.PerCountryItem

class AppGeneratedSource {

    fun getPerCountryItem() = listOf(
        PerCountryItem(
            PerCountryItem.ID,
            R.string.country_indonesia,
            "https://indonesia-covid-19.mathdro.id/api",
            R.drawable.flag_indonesia
        )
    )
}
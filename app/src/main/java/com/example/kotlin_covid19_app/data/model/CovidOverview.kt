package com.example.kotlin_covid19_app.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CovidOverview(
    @Expose @SerializedName("confirmed") val confirmed: CovidOverviewItem? = null,
    @Expose @SerializedName("recovered") val recovered: CovidOverviewItem? = null,
    @Expose @SerializedName("deaths") val deaths: CovidOverviewItem? = null
)
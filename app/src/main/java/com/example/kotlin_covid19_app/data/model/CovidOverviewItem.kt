package com.example.kotlin_covid19_app.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CovidOverviewItem(
    @Expose @SerializedName("detail") val detail: String? = null,
    @Expose @SerializedName("value") val value: Int = 0
)
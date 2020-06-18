package com.example.kotlin_covid19_app.ui.base

import com.example.kotlin_covid19_app.ui.adapter.ItemTypeFactory

interface BaseViewItem {
    fun typeOf(itemFactory: ItemTypeFactory): Int = itemFactory.type(this)
}
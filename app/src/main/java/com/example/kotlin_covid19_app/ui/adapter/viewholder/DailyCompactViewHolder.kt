package com.example.kotlin_covid19_app.ui.adapter.viewholder

import android.view.View
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.databinding.ItemDailyCompactBinding
import com.example.kotlin_covid19_app.ui.adapter.BaseViewHolder
import com.example.kotlin_covid19_app.util.NumberUtils

class DailyCompactViewHolder(itemView: View) : BaseViewHolder<DailyItem>(itemView) {

    private val binding = ItemDailyCompactBinding.bind(itemView)

    override fun bind(item: DailyItem) {
        with(binding) {
            txtDate.text = NumberUtils.formatStringDate(item.reportDate)

            root.context?.let {
                txtConfirmed.text = it.getString(R.string.confirmed_case_count, NumberUtils.numberFormat(item.deltaConfirmed))
                txtRecovered.text = it.getString(R.string.recovered_case_count, NumberUtils.numberFormat(item.deltaRecovered))
            }
        }
    }

    override fun setOnClickListener(listener: (View) -> Unit) {
        // no op
    }

    companion object {
        val LAYOUT = R.layout.item_daily_compact
    }
}
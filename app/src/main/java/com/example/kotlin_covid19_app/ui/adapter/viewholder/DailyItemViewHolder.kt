package com.example.kotlin_covid19_app.ui.adapter.viewholder

import android.view.View
import androidx.core.content.ContextCompat
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.databinding.ItemDailyBinding
import com.example.kotlin_covid19_app.ui.adapter.BaseViewHolder
import com.example.kotlin_covid19_app.ui.base.BaseViewItem
import com.example.kotlin_covid19_app.util.IncrementStatus
import com.example.kotlin_covid19_app.util.NumberUtils

data class DailyItem(
    val deltaConfirmed: Int = 0,
    val deltaRecovered: Int = 0,
    val mainlandChina: Int = 0,
    val otherLocations: Int = 0,
    val reportDate: String = "",
    var incrementRecovered: Int = IncrementStatus.FLAT,
    var incrementConfirmed: Int = IncrementStatus.FLAT
): BaseViewItem

class DailyItemViewHolder(itemView: View) : BaseViewHolder<DailyItem>(itemView) {
    private val binding: ItemDailyBinding = ItemDailyBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        binding.root.setOnClickListener { listener.invoke(it) }
    }

    override fun bind(item: DailyItem) {
        with(binding) {
            txtDate.text = NumberUtils.formatStringDate(item.reportDate)

            root.context?.let {
                txtInformation.text = it.getString(
                    R.string.information_location_total_case,
                    NumberUtils.numberFormat(item.mainlandChina),
                    NumberUtils.numberFormat(item.otherLocations))

                txtConfirmed.text = it.getString(R.string.confirmed_case_count, NumberUtils.numberFormat(item.deltaConfirmed))
                txtRecovered.text = it.getString(R.string.recovered_case_count, NumberUtils.numberFormat(item.deltaRecovered))

                imgRecovered.setImageDrawable(ContextCompat.getDrawable(it,getFluctuationIcon(item.incrementRecovered)))
                imgConfirmed.setImageDrawable(ContextCompat.getDrawable(it, getFluctuationIcon(item.incrementConfirmed)))
            }
        }
    }

    private fun getFluctuationIcon(status: Int) = when (status) {
        IncrementStatus.INCREASE -> R.drawable.ic_trending_up
        IncrementStatus.DECREASE -> R.drawable.ic_trending_down
        else -> R.drawable.ic_trending_flat
    }

    companion object {
        const val LAYOUT = R.layout.item_daily
    }
}
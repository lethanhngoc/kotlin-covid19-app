package com.example.kotlin_covid19_app.ui.adapter.viewholder

import android.view.View
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.databinding.ItemPinnedBinding
import com.example.kotlin_covid19_app.ui.adapter.BaseViewHolder
import com.example.kotlin_covid19_app.ui.base.BaseViewItem
import com.example.kotlin_covid19_app.util.NumberUtils

data class PinnedItem(
    val confirmed: Int? = null,
    val recovered: Int? = null,
    val deaths: Int? = null,
    val locationName: String,
    val lastUpdate: Long
): BaseViewItem

class PinnedItemViewHolder(itemView: View) : BaseViewHolder<PinnedItem>(itemView) {
    private val binding: ItemPinnedBinding = ItemPinnedBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        //Listener
    }

    override fun bind(item: PinnedItem) {
        with(binding) {
            val lastUpdate = NumberUtils.formatTime(item.lastUpdate)
            txtLocation.text = item.locationName
            txtUpdate.text = itemView.context.getString(R.string.information_last_update, lastUpdate)
            txtData.text = NumberUtils.numberFormat(item.confirmed)
            txtRcv.text = NumberUtils.numberFormat(item.recovered)
            txtDeath.text = NumberUtils.numberFormat(item.deaths)
        }
    }

    companion object {
        const val LAYOUT = R.layout.item_pinned
    }
}
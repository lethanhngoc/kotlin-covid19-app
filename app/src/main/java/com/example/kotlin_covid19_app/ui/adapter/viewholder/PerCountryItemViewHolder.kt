package com.example.kotlin_covid19_app.ui.adapter.viewholder

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.databinding.ItemPerCountryBinding
import com.example.kotlin_covid19_app.ui.adapter.BaseViewHolder
import com.example.kotlin_covid19_app.ui.base.BaseViewItem
import com.example.kotlin_covid19_app.util.ext.getString

data class PerCountryItem(
    val id: Int,
    @StringRes val country: Int,
    val source: String,
    @DrawableRes val icon: Int
) : BaseViewItem {

    companion object {
        /*Generated country ID for identifier*/
        const val ID = 1
        const val MY = 2
        const val UK = 3
    }
}

class PerCountryViewHolder(itemView: View) : BaseViewHolder<PerCountryItem>(itemView) {
    private val binding: ItemPerCountryBinding = ItemPerCountryBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        binding.root.setOnClickListener { listener.invoke(it) }
    }

    override fun bind(item: PerCountryItem) {
        with(binding) {
            txtCountry.text = getString(item.country)
            txtInformation.text = itemView.context.getString(R.string.source, item.source)
            imgIcon.setImageDrawable(itemView.context.resources.getDrawable(item.icon))
        }
    }


    companion object {
        const val LAYOUT = R.layout.item_per_country
    }
}
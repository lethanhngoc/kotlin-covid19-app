package com.example.kotlin_covid19_app.ui.adapter.viewholder

import android.view.View
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.databinding.ItemTextBinding
import com.example.kotlin_covid19_app.ui.adapter.BaseViewHolder
import com.example.kotlin_covid19_app.ui.base.BaseViewItem
import com.example.kotlin_covid19_app.util.ext.gone
import com.example.kotlin_covid19_app.util.ext.visible

data class TextItem(
    val textResId: Int? = null,
    val textActionResId: Int? = null
) : BaseViewItem

class TextItemViewHolder(itemView: View) : BaseViewHolder<TextItem>(itemView) {
    private val binding: ItemTextBinding = ItemTextBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        binding.textAction.setOnClickListener(listener)
    }

    override fun bind(item: TextItem) {
        with(binding) {
            root.context?.let { context ->
                textTitle.text = item.textResId?.let { context.getString(it) }.orEmpty()
                if (item.textActionResId != null) {
                    with(textAction) {
                        visible()
                        text = context.getString(item.textActionResId)
                    }
                } else {
                    textAction.gone()
                }
            }
        }
    }

    companion object {
        const val LAYOUT = R.layout.item_text
    }
}
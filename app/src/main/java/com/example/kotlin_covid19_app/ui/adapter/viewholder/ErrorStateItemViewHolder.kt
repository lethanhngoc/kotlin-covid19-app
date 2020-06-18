package com.example.kotlin_covid19_app.ui.adapter.viewholder

import android.view.View
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.databinding.ItemErrorStateBinding
import com.example.kotlin_covid19_app.ui.adapter.BaseViewHolder
import com.example.kotlin_covid19_app.ui.base.BaseViewItem

class ErrorStateItem(
    val titleResId: Int,
    val subtitleResId: Int
): BaseViewItem

class ErrorStateItemViewHolder(itemView: View) : BaseViewHolder<ErrorStateItem>(itemView) {

    private val binding: ItemErrorStateBinding = ItemErrorStateBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        //Listener
        binding.textRetry.setOnClickListener { listener.invoke(it) }
    }

    override fun bind(item: ErrorStateItem) {
        with(binding){
            textTitle.text = itemView.context.getString(item.titleResId)
            textSubtitle.text = itemView.context.getString(item.subtitleResId)
        }
    }

    companion object {
        const val LAYOUT = R.layout.item_error_state
    }
}
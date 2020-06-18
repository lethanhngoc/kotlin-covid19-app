package com.example.kotlin_covid19_app.ui.adapter.viewholder

import android.view.View
import com.example.kotlin_covid19_app.R
import com.example.kotlin_covid19_app.ui.adapter.BaseViewHolder
import com.example.kotlin_covid19_app.ui.base.BaseViewItem

class LoadingStateItem: BaseViewItem

class LoadingStateItemViewHolder(itemView: View) : BaseViewHolder<LoadingStateItem>(itemView) {

    override fun setOnClickListener(listener: (View) -> Unit) {
        //Listener
    }

    override fun bind(item: LoadingStateItem) {

    }

    companion object {
        const val LAYOUT = R.layout.item_loading_state
    }
}
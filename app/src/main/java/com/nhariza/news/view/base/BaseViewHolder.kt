package com.nhariza.news.view.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(item: T) {
        // Implements for each view holder
    }
}
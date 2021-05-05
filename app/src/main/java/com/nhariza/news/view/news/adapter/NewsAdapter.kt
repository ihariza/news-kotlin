package com.nhariza.news.view.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.nhariza.news.databinding.ReportRowBinding
import com.nhariza.news.repository.model.Report

class NewsAdapter internal constructor(
    private val requestManager: RequestManager,
    private val reportListener: ReportListener?
) :
    RecyclerView.Adapter<ReportViewHolder>() {

    fun interface ReportListener {
        fun onReportClicked(reportId: String)
    }

    private val news: MutableList<Report> = ArrayList()
    private val imageSizeProvider: ViewPreloadSizeProvider<Any> = ViewPreloadSizeProvider()
    private var clearNews = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val reportRowBinding = ReportRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        imageSizeProvider.setView(reportRowBinding.image)
        return ReportViewHolder(reportRowBinding, requestManager, reportListener)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun addAll(collection: Collection<Report>) {
        if (clearNews) {
            news.clear()
            clearNews = false
        }
        news.addAll(collection)
        notifyDataSetChanged()
    }

    fun clear() {
        clearNews = true
    }
}
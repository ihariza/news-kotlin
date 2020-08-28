package com.ihariza.news.presentation.view.news.adapter

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.ihariza.news.databinding.NewRowBinding
import com.ihariza.news.presentation.model.Report
import com.ihariza.news.presentation.view.base.BaseViewHolder
import com.ihariza.news.presentation.view.news.adapter.NewsAdapter.ReportListener

internal class NewsViewHolder(private val newsRowBinding: NewRowBinding,
                              private val requestManager: RequestManager,
                              private val reportListener: ReportListener) :
        BaseViewHolder<Report>(newsRowBinding.root) {

    override fun bind(item: Report) {
        renderReportImage(item.image)
        renderReportTitle(item.title)
        renderReportDescription(item.description)
        renderReportAuthor(item.author)
        renderReportPublished(item.published)
        onItemClick(item)
    }

    private fun renderReportImage(urlImage: String?) {
        getImage(urlImage, newsRowBinding.image)
    }

    private fun renderReportTitle(name: String?) {
        newsRowBinding.title.text = name
    }

    private fun renderReportDescription(description: String?) {
        newsRowBinding.description.text = description
    }

    private fun renderReportAuthor(author: String?) {
        newsRowBinding.author.text = author
    }

    private fun renderReportPublished(published: String) {
        newsRowBinding.published.text = published
    }

    private fun onItemClick(report: Report) {
        itemView.setOnClickListener { reportListener.onReportClicked(report.id) }
    }

    private fun getImage(photo: String?, photoImageView: ImageView) {
        requestManager.load(photo).centerCrop().into(photoImageView)
    }
}
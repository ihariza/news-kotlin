package com.ihariza.news.presentation.view.news.adapter

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.ihariza.news.databinding.ReportRowBinding
import com.ihariza.news.presentation.model.Report
import com.ihariza.news.presentation.view.base.BaseViewHolder
import com.ihariza.news.presentation.view.news.adapter.NewsAdapter.ReportListener

internal class ReportViewHolder(private val reportRowBinding: ReportRowBinding,
                                private val requestManager: RequestManager,
                                private val reportListener: ReportListener) :
        BaseViewHolder<Report>(reportRowBinding.root) {

    override fun bind(item: Report) {
        renderReportImage(item.image)
        renderReportTitle(item.title)
        renderReportDescription(item.description)
        renderReportAuthor(item.author)
        renderReportPublished(item.published)
        onItemClick(item)
    }

    private fun renderReportImage(urlImage: String?) {
        getImage(urlImage, reportRowBinding.image)
    }

    private fun renderReportTitle(name: String?) {
        reportRowBinding.title.text = name
    }

    private fun renderReportDescription(description: String?) {
        reportRowBinding.description.text = description
    }

    private fun renderReportAuthor(author: String?) {
        reportRowBinding.author.text = author
    }

    private fun renderReportPublished(published: String) {
        reportRowBinding.published.text = published
    }

    private fun onItemClick(report: Report) {
        itemView.setOnClickListener { reportListener.onReportClicked(report.id) }
    }

    private fun getImage(photo: String?, photoImageView: ImageView) {
        requestManager.load(photo).centerCrop().into(photoImageView)
    }
}
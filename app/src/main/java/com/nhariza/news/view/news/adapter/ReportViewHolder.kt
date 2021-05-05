package com.nhariza.news.view.news.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nhariza.news.databinding.ReportRowBinding
import com.nhariza.news.repository.model.Report
import com.nhariza.news.view.news.adapter.NewsAdapter.ReportListener
import com.nhariza.news.view.util.DateFormatter

class ReportViewHolder(
    private val reportRowBinding: ReportRowBinding,
    private val requestManager: RequestManager,
    private val reportListener: ReportListener?
) : RecyclerView.ViewHolder(reportRowBinding.root) {

    fun bind(item: Report) {
        renderReportImage(item.image)
        renderReportTitle(item.title)
        renderReportDescription(item.description)
        renderReportAuthor(item.author)
        renderReportPublished(DateFormatter.getRelativeTime(item.published))
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
        reportRowBinding.root.setOnClickListener { reportListener?.onReportClicked(report.id) }
    }

    private fun getImage(photo: String?, photoImageView: ImageView) {
        requestManager.load(photo).centerCrop().into(photoImageView)
    }
}
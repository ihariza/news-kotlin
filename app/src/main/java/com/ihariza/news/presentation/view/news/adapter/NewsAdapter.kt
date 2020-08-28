package com.ihariza.news.presentation.view.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader.PreloadModelProvider
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.ihariza.news.databinding.LoadingRowBinding
import com.ihariza.news.databinding.NewRowBinding
import com.ihariza.news.presentation.model.Loading
import com.ihariza.news.presentation.model.Report
import com.ihariza.news.presentation.view.base.BaseViewHolder

class NewsAdapter internal constructor(private val requestManager: RequestManager,
                                       private val reportListener: ReportListener) :
        RecyclerView.Adapter<BaseViewHolder<*>>(), PreloadModelProvider<Any> {

    fun interface ReportListener {
        fun onReportClicked(reportId: String)
    }

    private val news: MutableList<Any> = ArrayList()
    private val loading = Loading()
    private val imageSizeProvider: ViewPreloadSizeProvider<Any> = ViewPreloadSizeProvider()
    private var isLoading = false
    private var clearNews = false

    override fun getItemViewType(position: Int): Int =
            when {
                news[position] is Loading -> VIEW_TYPE_LOADING
                else -> VIEW_TYPE_NORMAL
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val newRowBinding = NewRowBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                imageSizeProvider.setView(newRowBinding.image)
                NewsViewHolder(newRowBinding, requestManager, reportListener)
            }
            VIEW_TYPE_LOADING -> LoadingViewHolder(
                    LoadingRowBinding.inflate(LayoutInflater.from(parent.context),
                            parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is NewsViewHolder -> holder.bind(news[position] as Report)
            is LoadingViewHolder -> holder.bind(loading)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun getPreloadItems(position: Int): List<*> {
        return listOf(news[position])
    }

    override fun getPreloadRequestBuilder(item: Any): RequestBuilder<*>? {
        return when (item) {
            is Report -> requestManager.load(item.image).centerCrop().priority(Priority.HIGH)
            else -> null
        }
    }

    fun getImageSizeProvider(): ViewPreloadSizeProvider<Any> {
        return imageSizeProvider
    }

    fun addAll(collection: Collection<Report>?) {
        if (clearNews) {
            news.clear()
            clearNews = false
        }
        news.addAll(collection!!)
        notifyDataSetChanged()
    }

    fun clear() {
        clearNews = true
    }

    fun showLoading() {
        isLoading = true
        news.add(loading)
        notifyDataSetChanged()
    }

    fun hideLoading() {
        isLoading = false
        news.remove(loading)
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_NORMAL = 1
    }

}
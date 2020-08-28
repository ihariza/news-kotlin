package com.ihariza.news.presentation.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Supporting only LinearLayoutManager for now.
 */
abstract class PaginationListener(private val layoutManager: LinearLayoutManager,
                                  private val pageSize: Int): RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading()
                && !isLastPage()
                && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0 && totalItemCount >= pageSize) {
            loadPage(totalItemCount / pageSize + 1)
        }
    }

    abstract fun loadPage(pageNumber: Int)
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}
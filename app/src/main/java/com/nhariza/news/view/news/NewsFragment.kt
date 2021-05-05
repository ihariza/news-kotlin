package com.nhariza.news.view.news

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.nhariza.news.R
import com.nhariza.news.databinding.FragmentNewsBinding
import com.nhariza.news.view.base.BaseFragment
import com.nhariza.news.view.base.EventObserver
import com.nhariza.news.view.news.adapter.NewsAdapter
import com.nhariza.news.view.util.Constants
import com.nhariza.news.view.widget.MarginItemDecoration
import com.nhariza.news.view.widget.PaginationListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment : BaseFragment() {

    private val viewModel: NewsViewModel by viewModel()

    private val glideRequestManager: RequestManager by inject()

    private val adapter: NewsAdapter by lazy {
        NewsAdapter(glideRequestManager) { viewModel.openReport(it) }
    }

    lateinit var binding: FragmentNewsBinding


    override fun layoutRes(): Int = R.layout.fragment_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        setupToolbar()
        setupSwipeRefresh()
        setupRecyclerView()
        setupObservers()
        if (adapter.itemCount == 0) {
            viewModel.getNewsPage(Constants.START_NEWS_PAGE)
        }
    }

    private fun setupToolbar() {
        baseActivity?.setSupportActionBar(binding.toolbar)
        baseActivity?.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(), R.color.blue_700
            )
        )
        binding.swipeRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.refreshNews()
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        val paginationListener: PaginationListener =
            object : PaginationListener(layoutManager, Constants.NEWS_PAGE_SIZE) {
                override fun loadPage(pageNumber: Int) {
                    viewModel.getNewsPage(pageNumber)
                }

                override fun isLastPage(): Boolean =
                    viewModel.lastPageEvent.value?.peekContent() ?: false
            }
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.spacing_large).toInt()
            )
        )
        binding.recyclerview.adapter = adapter
        binding.recyclerview.addOnScrollListener(paginationListener)
    }

    private fun setupObservers() {
        viewModel.refreshingEvent.observe(viewLifecycleOwner, EventObserver {
            if (!it) {
                binding.swipeRefresh.isRefreshing = false
            }
        })

        viewModel.errorEvent.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.news.observe(viewLifecycleOwner, {
            adapter.addAll(it)
        })

        viewModel.openReportEvent.observe(viewLifecycleOwner, EventObserver {
            openReport(it)
        })
    }

    private fun openReport(reportId: String) {
        val action = NewsFragmentDirections.actionNewsFragmentToReportFragment(reportId)
        findNavController().navigate(action)
    }
}
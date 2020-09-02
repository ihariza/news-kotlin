package com.ihariza.news.presentation.view.report

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ihariza.news.R
import com.ihariza.news.databinding.FragmentReportBinding
import com.ihariza.news.presentation.event.EventObserver
import com.ihariza.news.presentation.model.Report
import com.ihariza.news.presentation.view.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReportFragment : BaseFragment() {

    private val args: ReportFragmentArgs by navArgs()

    private val viewModel: ReportViewModel by viewModel()

    private lateinit var binding: FragmentReportBinding


    override fun layoutRes(): Int = R.layout.fragment_report

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportBinding.bind(view)
        setupToolbar()
        setupObservers()
        viewModel.showReport(args.reportId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_report, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
            R.id.action_share -> viewModel.shareReport()
            R.id.action_open -> viewModel.openWebReport()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
        baseActivity?.setSupportActionBar(binding.toolbar)
        baseActivity?.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupObservers() {
        viewModel.errorEvent.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.shareReportEvent.observe(viewLifecycleOwner, EventObserver {
            showShareReport(it)
        })

        viewModel.openWebReportEvent.observe(viewLifecycleOwner, EventObserver {
            showOpenReport(it)
        })

        viewModel.report.observe(viewLifecycleOwner, {
            showTitle(it?.title)
            showSubtitle(it?.author)
            showReport(it?.url)
        })
    }

    private fun showTitle(title: String?) {
        binding.toolbar.title = title
    }

    private fun showSubtitle(subtitle: String?) {
        binding.toolbar.subtitle = subtitle
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showReport(url: String?) {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?)
                    : Boolean {
                //To open hyperlink in existing WebView
                view?.loadUrl(request?.url.toString())
                return false
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                binding.progressReport.visibility = View.VISIBLE
                binding.progressReport.progress = 0
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressReport.visibility = View.GONE
            }
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                binding.progressReport.progress = newProgress
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        url?.let { binding.webView.loadUrl(it) }
    }

    private fun showShareReport(report: Report) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, report.title)
        shareIntent.putExtra(Intent.EXTRA_TEXT, report.url)
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }

    private fun showOpenReport(url: String) {
        val viewIntent = Intent(Intent.ACTION_VIEW)
        viewIntent.data = Uri.parse(url)
        if (viewIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(viewIntent)
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
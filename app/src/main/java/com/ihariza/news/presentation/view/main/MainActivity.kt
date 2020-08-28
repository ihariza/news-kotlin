package com.ihariza.news.presentation.view.main

import androidx.viewbinding.ViewBinding
import com.ihariza.news.databinding.ActivityMainBinding
import com.ihariza.news.presentation.view.base.BaseActivity

class MainActivity : BaseActivity() {

    private val binding: ViewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun getViewBinding(): ViewBinding = binding
}

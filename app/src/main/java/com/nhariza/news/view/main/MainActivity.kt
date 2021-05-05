package com.nhariza.news.view.main

import androidx.viewbinding.ViewBinding
import com.nhariza.news.databinding.ActivityMainBinding
import com.nhariza.news.view.base.BaseActivity

class MainActivity : BaseActivity() {

    private val binding: ViewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun getViewBinding(): ViewBinding = binding
}

package com.project.ini.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.ini.data.repo.NewsRepository
import com.project.ini.ui.detail.DetailViewModel
import com.project.ini.ui.main.ui.bookmark.BookmarkViewModel
import com.project.ini.ui.main.ui.home.HomeViewModel

class DetailViewModelFactory constructor(
    private val url: String,
    private val newsRepository: NewsRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            DetailViewModel(url,newsRepository) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
package com.project.ini.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.ini.data.repo.NewsRepository
import com.project.ini.ui.main.ui.bookmark.BookmarkViewModel
import com.project.ini.ui.main.ui.home.HomeViewModel

class MainViewModelFactory constructor(
    private val newsRepository: NewsRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(newsRepository) as T
        } else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            BookmarkViewModel(newsRepository) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
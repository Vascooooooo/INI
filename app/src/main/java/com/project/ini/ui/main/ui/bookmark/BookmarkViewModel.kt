package com.project.ini.ui.main.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.ini.data.repo.NewsRepository

class BookmarkViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getAllBookmarks() = newsRepository.getBookmark()
}
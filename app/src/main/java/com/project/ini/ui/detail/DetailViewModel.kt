package com.project.ini.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.project.ini.data.models.ArticlesItem
import com.project.ini.data.repo.NewsRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val url: String,
    private val newsRepository: NewsRepository
) : ViewModel() {
    val isBookmarked = MutableLiveData<Boolean>()

    init {
        checkBookmark()
    }

    fun insert(articlesItem: ArticlesItem) {
        newsRepository.insert(articlesItem)
    }

    fun delete() {
        newsRepository.delete(url)
    }

    fun checkBookmark() {
        viewModelScope.launch {
            newsRepository.checkBookmark(url).asFlow().collect {
                isBookmarked.postValue(it.isNotEmpty())
            }
        }
    }
}
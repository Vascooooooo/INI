package com.project.ini.ui.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.project.ini.data.models.ArticlesItem
import com.project.ini.data.repo.NewsRepository
import com.project.ini.utils.Result
import kotlinx.coroutines.launch

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listNews = MutableLiveData<List<ArticlesItem>>(arrayListOf())
    val listNews: LiveData<List<ArticlesItem>> = _listNews

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            newsRepository.getNews().asFlow().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isError.postValue(false)
                        _isLoading.postValue(true)
                    }

                    is Result.Success -> {
                        _isError.postValue(false)
                        _isLoading.postValue(false)
                        _listNews.postValue(result.data.articles as List<ArticlesItem>?)
                    }

                    is Result.Error -> {
                        _isError.postValue(true)
                        _isLoading.postValue(false)
                        _toastMessage.postValue(result.error)
                    }
                }
            }
        }
    }
}
package com.project.ini.data.repo

import android.app.Application
import androidx.lifecycle.liveData
import com.project.ini.data.local.BookmarkDao
import com.project.ini.data.local.BookmarkRoomDatabase
import com.project.ini.data.models.ArticlesItem
import com.project.ini.data.remote.ApiConfig
import com.project.ini.data.remote.ApiService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.project.ini.utils.Result

class NewsRepository(
    private val application: Application,
    private val apiService: ApiService = ApiConfig.getApiService(),
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
) {
    private var bookmarkDao: BookmarkDao

    init {
        val db = BookmarkRoomDatabase.getInstance(application)
        bookmarkDao = db.bookmarkDao()
    }

    fun getNews() = liveData {
        emit(Result.Loading)
        try {
            val storyResponse = apiService.getNews()
            emit(Result.Success(storyResponse))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun insert(bookmark: ArticlesItem) {
        executorService.execute { bookmarkDao.insert(bookmark) }
    }

    fun delete(url: String) {
        executorService.execute { bookmarkDao.delete(url) }
    }

    fun getBookmark() = bookmarkDao.getAllBookmark()

    fun checkBookmark(url: String) = bookmarkDao.checkBookmark(url)

    companion object {
        @Volatile
        private var INSTANCE: NewsRepository? = null

        @JvmStatic
        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(this) {
                NewsRepository(application).apply {
                    INSTANCE = this
                }
            }
    }
}
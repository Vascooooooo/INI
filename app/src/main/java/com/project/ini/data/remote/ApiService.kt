package com.project.ini.data.remote

import com.project.ini.BuildConfig
import com.project.ini.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?apiKey=${BuildConfig.APIKEY}&sources=bbc-news")
    suspend fun getNews(): NewsResponse
}
package com.slowmotion.newsapp.service

import com.slowmotion.newsapp.model.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET ("top-headlines")
    fun getNewsHeadLines(
        @Query("country") country:String?,
        @Query("apiKey") apiKey:String?
    ): Call<ResponseNews>

}
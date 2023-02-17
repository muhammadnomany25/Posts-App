package com.stc.stcassignment.data.network

import com.stc.stcassignment.data.network.response.ArticleListResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ArticleApi {

    @GET("top-headlines?")
    suspend fun getArticles(
        @Header("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): ArticleListResponse

}
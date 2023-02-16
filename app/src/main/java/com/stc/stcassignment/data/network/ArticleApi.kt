package com.stc.stcassignment.data.network

import com.stc.stcassignment.data.network.response.ArticleListResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ArticleApi {

    @GET("search")
    suspend fun getArticles(
        @Header("x-api-key") apiKey: String,
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
    ): ArticleListResponse

}
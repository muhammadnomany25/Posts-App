package com.stc.stcassignment.data.network.response

import com.google.gson.annotations.SerializedName
import com.stc.stcassignment.data.network.dto.article.ArticleDto

data class ArticleListResponse(
    @SerializedName("articles") val articles: List<ArticleDto>,

    @SerializedName("status") val status: String?,

    @SerializedName("totalResults") val totalResults: Int,
)


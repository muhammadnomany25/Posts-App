package com.stc.stcassignment.data.network.dto.article

import com.google.gson.annotations.SerializedName

class ArticleDto(

    @SerializedName("title") val title: String?,

    @SerializedName("content") val content: String?,

    @SerializedName("urlToImage") val urlToImage: String?,
)
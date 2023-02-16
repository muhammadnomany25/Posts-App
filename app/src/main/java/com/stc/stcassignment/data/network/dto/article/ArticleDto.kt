package com.stc.stcassignment.data.network.dto.article

import com.google.gson.annotations.SerializedName

class ArticleDto(
    @SerializedName("_id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("media") val media: String?,
)
package com.stc.stcassignment.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val title: String,
    val content: String,
    val urlToImage: String,
)
package com.stc.stcassignment.presentation.article.list

import androidx.paging.PagingData
import com.stc.stcassignment.domain.model.Article

data class ArticlesUiState(
    val articles: PagingData<Article>? = null,
)

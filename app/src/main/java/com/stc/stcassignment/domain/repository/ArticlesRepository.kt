package com.stc.stcassignment.domain.repository

import androidx.paging.PagingData
import com.stc.stcassignment.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {

    fun getArticles(): Flow<PagingData<Article>>
}
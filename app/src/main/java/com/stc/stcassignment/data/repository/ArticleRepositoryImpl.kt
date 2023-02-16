package com.stc.stcassignment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.stc.stcassignment.data.network.ArticleApi
import com.stc.stcassignment.data.network.dto.article.ArticleDtoMapper
import com.stc.stcassignment.domain.model.Article
import com.stc.stcassignment.domain.repository.ArticlesRepository
import com.stc.stcassignment.domain.util.Constants.Companion.PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepositoryImpl
@Inject constructor(
    private val api: ArticleApi,
    private val articleDtoMapper: ArticleDtoMapper,
) : ArticlesRepository {

    override fun getArticles(): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(PAGINATION_PAGE_SIZE),
            pagingSourceFactory = { ArticlePagingSource(api, articleDtoMapper) }).flow
    }
}
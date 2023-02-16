package com.stc.stcassignment.domain.usecase

import androidx.paging.PagingData
import com.stc.stcassignment.domain.model.Article
import com.stc.stcassignment.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesUseCase
@Inject
constructor(
    private val articlesRepository: ArticlesRepository,
) {

    operator fun invoke(): Flow<PagingData<Article>> = articlesRepository.getArticles()
}
package com.stc.stcassignment.di

import com.stc.stcassignment.data.network.ArticleApi
import com.stc.stcassignment.data.network.dto.article.ArticleDtoMapper
import com.stc.stcassignment.data.repository.ArticleRepositoryImpl
import com.stc.stcassignment.domain.repository.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataDiModule {

    @Singleton
    @Provides
    fun provideArticleDtoMapper(): ArticleDtoMapper {
        return ArticleDtoMapper()
    }

    @Singleton
    @Provides
    fun provideArticleRepository(
        api: ArticleApi,
        articleDtoMapper: ArticleDtoMapper,
    ) = ArticleRepositoryImpl(api, articleDtoMapper) as ArticlesRepository

}




















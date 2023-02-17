package com.stc.stcassignment.di

import android.content.Context
import com.stc.stcassignment.data.local.ArticleDao
import com.stc.stcassignment.data.local.ArticlesDatabase
import com.stc.stcassignment.data.network.ArticleApi
import com.stc.stcassignment.data.network.dto.article.ArticleDtoMapper
import com.stc.stcassignment.data.repository.ArticleRepositoryImpl
import com.stc.stcassignment.domain.repository.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideDatabase(@ApplicationContext appContext: Context) =
        ArticlesDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideArticlesDao(db: ArticlesDatabase) = db.getArticlesDao()

    @Singleton
    @Provides
    fun provideArticleRepository(
        api: ArticleApi,
        articleDtoMapper: ArticleDtoMapper,
        localSource: ArticleDao,
    ) = ArticleRepositoryImpl(api, articleDtoMapper, localSource) as ArticlesRepository

}




















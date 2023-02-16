package com.stc.stcassignment.di

import com.stc.stcassignment.domain.repository.ArticlesRepository
import com.stc.stcassignment.domain.usecase.GetArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesDiModule {

    @Singleton
    @Provides
    fun provideGetArticlesUseCase(
        articleRepository: ArticlesRepository,
    ): GetArticlesUseCase {
        return GetArticlesUseCase(articleRepository)
    }
}




















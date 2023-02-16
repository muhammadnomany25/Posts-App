package com.stc.stcassignment.data.network.dto.article

import com.stc.stcassignment.domain.model.Article
import com.stc.stcassignment.domain.util.DomainMapper

class ArticleDtoMapper : DomainMapper<ArticleDto, Article> {

    override fun mapToDomainModel(model: ArticleDto): Article {
        return Article(title = model.title, id = model.id, media = model.media)
    }

    override fun mapFromDomainModel(domainModel: Article): ArticleDto {
        return ArticleDto(title = domainModel.title, id = domainModel.id, media = domainModel.media)
    }

    fun toDomainList(initial: List<ArticleDto>): List<Article> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Article>): List<ArticleDto> {
        return initial.map { mapFromDomainModel(it) }
    }

}
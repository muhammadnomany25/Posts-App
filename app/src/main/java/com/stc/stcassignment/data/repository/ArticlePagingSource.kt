package com.stc.stcassignment.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stc.stcassignment.data.network.ArticleApi
import com.stc.stcassignment.data.network.dto.article.ArticleDtoMapper
import com.stc.stcassignment.domain.model.Article
import com.stc.stcassignment.domain.util.Constants.Companion.API_KEY
import com.stc.stcassignment.domain.util.Constants.Companion.PAGINATION_PAGE_SIZE
import com.stc.stcassignment.domain.util.Constants.Companion.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ArticlePagingSource
@Inject constructor(
    private val api: ArticleApi,
    private val articleDtoMapper: ArticleDtoMapper,
    private val country: String,
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val response = api.getArticles(API_KEY, country, page, PAGINATION_PAGE_SIZE)
            val articles = articleDtoMapper.toDomainList(response.articles)

            LoadResult.Page(data = articles,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = page + 1)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
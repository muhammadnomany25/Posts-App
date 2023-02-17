package com.stc.stcassignment.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stc.stcassignment.data.network.ArticleApi
import com.stc.stcassignment.data.network.dto.article.ArticleDtoMapper
import com.stc.stcassignment.domain.model.Article
import com.stc.stcassignment.domain.util.Constants.Companion.API_KEY
import com.stc.stcassignment.domain.util.Constants.Companion.COUNTRY_SEARCH_QUERY
import com.stc.stcassignment.domain.util.Constants.Companion.MAX_ALLOWED_RESULT
import com.stc.stcassignment.domain.util.Constants.Companion.PAGINATION_PAGE_SIZE
import com.stc.stcassignment.domain.util.Constants.Companion.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ArticlePagingSource
@Inject constructor(
    private val api: ArticleApi,
    private val articleDtoMapper: ArticleDtoMapper,
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
            // Don't load any further page if loaded results is 100 result
            // as I am using free account for the API and it only provide with a max of first 100 results
            val loadedItemsCount = page * PAGINATION_PAGE_SIZE
            if (loadedItemsCount >= MAX_ALLOWED_RESULT) {
                return LoadResult.Page(data = emptyList(),
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = null)
            }
            val response =
                api.getArticles(API_KEY, COUNTRY_SEARCH_QUERY, page, PAGINATION_PAGE_SIZE)
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
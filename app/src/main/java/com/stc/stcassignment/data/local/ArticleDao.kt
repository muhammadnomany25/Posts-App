package com.stc.stcassignment.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stc.stcassignment.domain.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(articles: List<Article>)

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<Article>

    @Query("Delete FROM articles")
    suspend fun deleteAllArticles()
}
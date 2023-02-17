package com.stc.stcassignment.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stc.stcassignment.domain.model.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
abstract class ArticlesDatabase : RoomDatabase() {

    abstract fun getArticlesDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: ArticlesDatabase? = null

        fun getDatabase(context: Context): ArticlesDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, ArticlesDatabase::class.java, "articles_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
package com.stc.stcassignment.presentation.article.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stc.stcassignment.databinding.ArticleListItemBinding
import com.stc.stcassignment.domain.model.Article
import com.stc.stcassignment.presentation.util.ArticleDiffCallback

class ArticlesListAdapter(
    private val clicked: (View, Article) -> Unit,
) : PagingDataAdapter<Article, ArticlesListAdapter.ArticleViewHolder>(ArticleDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ArticleViewHolder {
        return ArticleViewHolder(ArticleListItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    inner class ArticleViewHolder(
        private val binding: ArticleListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article?) {
            binding.tvTitle.text = item?.title

            binding.root.setOnClickListener {
                item?.let { articleItem -> clicked.invoke(it, articleItem) }
            }
        }
    }
}



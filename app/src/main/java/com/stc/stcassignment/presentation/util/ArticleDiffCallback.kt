package com.stc.stcassignment.presentation.util

import androidx.recyclerview.widget.DiffUtil
import com.stc.stcassignment.domain.model.Article

object ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.urlToImage == newItem.urlToImage
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}
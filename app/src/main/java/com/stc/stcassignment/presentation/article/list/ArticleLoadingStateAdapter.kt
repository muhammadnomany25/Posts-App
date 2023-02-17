package com.stc.stcassignment.presentation.article.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stc.stcassignment.R
import com.stc.stcassignment.databinding.ArticleLoadingStateItemBinding

class ArticleLoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ArticleLoadingStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState,
    ) = LoadStateViewHolder(parent, retry)


    override fun onBindViewHolder(
        holder: LoadStateViewHolder, loadState: LoadState,
    ) = holder.bind(loadState)

    inner class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit,
    ) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.article_loading_state_item, parent, false)) {

        private val binding = ArticleLoadingStateItemBinding.bind(itemView)
        private val retry: Button = binding.btnRetry.also {
            it.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {

            if (loadState is LoadState.Error) {
                binding.txtError.text = "Check Network Connection!"
            }

            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState is LoadState.Error
            binding.txtError.isVisible = loadState is LoadState.Error
        }
    }
}
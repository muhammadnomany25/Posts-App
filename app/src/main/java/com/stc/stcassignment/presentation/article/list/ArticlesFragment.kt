package com.stc.stcassignment.presentation.article.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.platform.MaterialElevationScale
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.stc.stcassignment.R
import com.stc.stcassignment.databinding.FragmentArticlesBinding
import com.stc.stcassignment.domain.model.Article
import com.stc.stcassignment.presentation.util.showRetry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesFragment : Fragment() {

    private val viewModel: ArticlesViewModel by viewModels()
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var articlesListAdapter: ArticlesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = 300L
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentArticlesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        initRecyclerView()
        handleLoadingStateAdapter()
        subscribeObserver()
        retry()
    }

    private fun initRecyclerView() {
        binding.articlesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            articlesListAdapter =
                ArticlesListAdapter { cardView: View, article: Article ->

                    exitTransition = MaterialElevationScale(false).apply {
                        duration = 300L
                    }
                    reenterTransition = MaterialElevationScale(true).apply {
                        duration = 300L
                    }
                    findNavController().navigate(
                        ArticlesFragmentDirections
                            .actionArticlesFragmentToArticleDetailFragment(article.title,
                                article.content,
                                article.urlToImage)
                    )
                }

            adapter = articlesListAdapter

            binding.articlesRecyclerView.adapter = articlesListAdapter.withLoadStateFooter(
                footer = ArticleLoadingStateAdapter(articlesListAdapter::retry)
            )
        }
    }

    private fun subscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.articles?.let { articlesListAdapter.submitData(it) }
                }
            }
        }
    }

    private fun handleLoadingStateAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                articlesListAdapter.addLoadStateListener { loadState ->
                    if (loadState.source.refresh is LoadState.Loading) {
                        if (articlesListAdapter.snapshot().isEmpty()) {
                            binding.shimmerLayout.startShimmer()
                        }

                        activity?.showRetry(false, null)

                    } else {
                        binding.shimmerLayout.apply {
                            stopShimmer()
                            visibility = View.GONE
                        }

                        val error = when {
                            loadState.source.prepend is LoadState.Error -> loadState.source.prepend as LoadState.Error
                            loadState.source.append is LoadState.Error -> loadState.source.append as LoadState.Error
                            loadState.source.refresh is LoadState.Error -> loadState.source.refresh as LoadState.Error
                            else -> null
                        }
                        error?.let {
                            Log.e("error33", "${error}")
                            if (articlesListAdapter.snapshot().isEmpty()) {
                                activity?.showRetry(true, "Check Network Connection!")
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                articlesListAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
            }
        }
    }

    private fun retry() {
        requireActivity().findViewById<Button>(R.id.btn_retry).setOnClickListener {
            articlesListAdapter.retry()
        }
    }

}
























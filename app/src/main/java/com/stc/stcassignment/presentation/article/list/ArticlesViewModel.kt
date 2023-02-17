package com.stc.stcassignment.presentation.article.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.stc.stcassignment.domain.usecase.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel
@Inject
constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticlesUiState())
    val uiState: StateFlow<ArticlesUiState> = _uiState.asStateFlow()

    init {
        onEvent(ArticlesEvents.GetArticles)
    }

    fun onEvent(event: ArticlesEvents) {
        when (event) {
            is ArticlesEvents.GetArticles -> {
                getArticles()
            }
        }
    }

    private fun getArticles() {
        viewModelScope.launch {
            delay(1000)
            getArticlesUseCase()
                .cachedIn(viewModelScope)
                .collect { articles ->
                    _uiState.update { it.copy(articles = articles) }
                }
        }
    }

}
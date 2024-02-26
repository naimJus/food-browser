package io.jusufinaim.foodbrowser.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.common.util.DoNothing
import io.jusufinaim.foodbrowser.common.util.isUnrecoverable
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.data.model.exception.FetchException.NetworkException.isFetchException
import io.jusufinaim.foodbrowser.di.qualifier.IoDispatcher
import io.jusufinaim.foodbrowser.di.qualifier.MainDispatcher
import io.jusufinaim.foodbrowser.domain.di.qualifiers.GetFoodQualifier
import io.jusufinaim.foodbrowser.domain.model.FoodItem
import io.jusufinaim.foodbrowser.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineContext,
    @MainDispatcher private val mainDispatcher: CoroutineContext,
    @GetFoodQualifier private val getFodUseCase: UseCase<String, Response<List<FoodItem>, FetchException>>
) : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.Initial)
    val uiStateFlow: StateFlow<SearchUiState> = _uiStateFlow.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable.isFetchException()) {
            _uiStateFlow.value = SearchUiState.Error(throwable as FetchException)
        } else if (throwable.isUnrecoverable()) {
            DoNothing
        } else {
            _uiStateFlow.value =
                SearchUiState.Error(FetchException.UnknownException("Something went wrong"))
        }
    }

    fun search(queryParam: String?) {
        if (queryParam.isNullOrEmpty() || queryParam.length < 3) {
            _uiStateFlow.value = SearchUiState.Initial
            return
        }
        viewModelScope.launch(mainDispatcher + coroutineExceptionHandler) {
            showProgress()
            val uiState = withContext(ioDispatcher) {
                getFodUseCase(queryParam).fold(
                    { data ->
                        if (data.isEmpty()) {
                            SearchUiState.Error(FetchException.NoItemsFoundException("Sorry, no items found with the name: $queryParam.\nTry searching for another food."))
                        } else {
                            SearchUiState.Success(false, data)
                        }
                    },
                    { error -> SearchUiState.Error(error) }
                )
            }
            _uiStateFlow.value = uiState
        }
    }

    private fun showProgress() {
        _uiStateFlow.update {
            when (it) {
                is SearchUiState.Error -> SearchUiState.Success(isLoading = true, emptyList())
                SearchUiState.Initial -> SearchUiState.Success(isLoading = true, emptyList())
                is SearchUiState.Success -> it.copy(isLoading = true, it.data)
            }
        }
    }
}
package io.jusufinaim.foodbrowser.ui.search

import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.domain.model.FoodItem

/**
 * [SearchUiState] is a sealed interface representing different states of the UI related to search operations.
 */
sealed interface SearchUiState {

    /**
     * [Initial] represents the initial state.
     */
    data object Initial : SearchUiState

    /**
     * [Success] represents the state when the search operation is successful.
     *
     * @param isLoading Indicates whether the UI is in a loading state. This is not a separate state because we don't want Success and IsLoading to be mutually exclusive.
     * @param data The list of [FoodItem] representing the search results.
     */
    data class Success(val isLoading: Boolean, val data: List<FoodItem>) : SearchUiState

    /**
     * [Error] represents the state when there is an error during the search operation.
     *
     * @param error The [FetchException] representing the error.
     */
    data class Error(val error: FetchException) : SearchUiState
}
package io.jusufinaim.foodbrowser.data.source

import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.data.model.remote.RemoteFood

/**
 * [FoodDataSource] is an internal interface representing a data source for retrieving food-related information.
 * Implementations of this interface are responsible for handling the retrieval of data from a remote/local source.
 */
internal interface FoodDataSource {

    /**
     * Performs a search operation for food items based on the provided [searchParam].
     *
     * @param searchParam The parameter used for searching food items.
     * @return A [Response] indicating the success or failure scenario of the search operation.
     *         - [Response]: Contains a list of [RemoteFood] representing the search results.
     *         - [Response.Failure]: Contains a [FetchException] representing the error in case of failure.
     */
    suspend fun search(searchParam: String): Response<List<RemoteFood>, FetchException>
}
package io.jusufinaim.foodbrowser.data.repository

import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.model.bridge.FoodBridge
import io.jusufinaim.foodbrowser.data.model.exception.FetchException

/**
 * [FoodRepository] is an interface representing a repository for handling the search operation related to food.
 * It defines a method for searching food items based on the provided query parameter.
 */
interface FoodRepository {

    /**
     * Performs a search operation for food items based on the provided query parameter.
     *
     * @param queryParam The parameter used for searching food items.
     * @return A [Response] indicating the success or failure of the search operation.
     *
     *         - [Response.Success]: Contains a list of [FoodBridge] representing the search results.
     *         - [Response.Failure]: Contains a [FetchException] representing the error in case of failure.
     */
    suspend fun search(queryParam: String): Response<List<FoodBridge>, FetchException>
}
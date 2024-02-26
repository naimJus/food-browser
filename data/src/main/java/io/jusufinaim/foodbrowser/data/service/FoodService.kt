package io.jusufinaim.foodbrowser.data.service

import io.jusufinaim.foodbrowser.data.model.remote.RemoteFood
import io.jusufinaim.foodbrowser.data.util.FOOD_QUERY_ENDPOINT
import io.jusufinaim.foodbrowser.data.util.FOOD_QUERY_PARAM_KEY
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [FoodService] is an interface representing a REST service for querying food-related information from a remote source.
 * It defines methods for interacting with the remote endpoint using annotations for HTTP CRUD requests.
 */
internal interface FoodService {

    /**
     * Performs a search operation for food items based on the provided query parameter.
     *
     * @param queryParam The parameter used for querying food items.
     * @return A list of [RemoteFood] representing the search results.
     */
    @GET(FOOD_QUERY_ENDPOINT)
    suspend fun search(@Query(FOOD_QUERY_PARAM_KEY) queryParam: String): List<RemoteFood>
}
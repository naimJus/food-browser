package io.jusufinaim.foodbrowser.data.repository

import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.model.bridge.FoodBridge
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.data.source.FoodDataSource
import io.jusufinaim.foodbrowser.data.util.mapToFoodBridge
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SearchFoodRepository @Inject constructor(private val remoteFoodDataSource: FoodDataSource) :
    FoodRepository {

    private val searchCache = mutableMapOf<String, List<FoodBridge>>()
    override suspend fun search(queryParam: String): Response<List<FoodBridge>, FetchException> {
        val cachedValues = searchCache[queryParam]
        if (!cachedValues.isNullOrEmpty()) {
            return Response.Success(cachedValues)
        }
        val foodResult = remoteFoodDataSource.search(queryParam).fold(
            { success ->
                val result = success.map { it.mapToFoodBridge() }
                searchCache[queryParam] = result
                Response.Success(result)
            },
            { failure -> Response.Failure(failure) }
        )
        return foodResult
    }
}
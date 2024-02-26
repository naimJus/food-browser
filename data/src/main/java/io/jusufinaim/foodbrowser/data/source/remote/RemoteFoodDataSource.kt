package io.jusufinaim.foodbrowser.data.source.remote

import com.google.gson.JsonParseException
import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.data.model.remote.RemoteFood
import io.jusufinaim.foodbrowser.data.service.FoodService
import io.jusufinaim.foodbrowser.data.source.FoodDataSource
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [RemoteFoodDataSource] is an internal class implementing [FoodDataSource] that communicates with a remote source
 * through [foodService] to perform searches for food items.
 *
 * @param foodService The service responsible for making network requests related to food items.
 */

@Singleton
internal class RemoteFoodDataSource @Inject constructor(private val foodService: FoodService) :
    FoodDataSource {

    /**
     * Performs a search operation for food items based on the provided [searchParam].
     *
     * @param searchParam The parameter used for searching food items.
     * @return A [Response] indicating the success or failure of the search operation.
     *         - [Response.Success]: Contains a list of [RemoteFood] representing the search results.
     *         - [Response.Failure]: Contains a [FetchException] representing the error in case of failure.
     */
    override suspend fun search(searchParam: String): Response<List<RemoteFood>, FetchException> {
        return try {
            Response.Success(foodService.search(searchParam))
        } catch (e: JsonParseException) {
            Response.Failure(FetchException.FetchItemsException("Cannot parse items ${e.cause?.message}"))
        } catch (e: UnknownHostException) {
            Response.Failure(FetchException.NetworkException)
        } catch (e: IOException) {
            Response.Failure(FetchException.FetchItemsException("Cannot fetch items ${e.cause?.message}"))
        } catch (e: Exception) {
            Response.Failure(FetchException.FetchItemsException(e.message ?: ""))
        }
    }
}
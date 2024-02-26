package io.jusufinaim.foodbrowser.domain.usecase

import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.data.repository.FoodRepository
import io.jusufinaim.foodbrowser.domain.model.FoodItem
import io.jusufinaim.foodbrowser.domain.util.mapToItem
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [GetFoodUseCase] is an internal class implementing the [UseCase] interface to get food-related information.
 *
 * @param foodRepository The repository providing access to food-related data.
 */
@Singleton
internal class GetFoodUseCase @Inject constructor(private val foodRepository: FoodRepository) :
    UseCase<String, Response<List<FoodItem>, FetchException>> {

    /**
     * Executes the use case to search for food items based on the provided argument [arg].
     *
     * @param arg The input argument for searching food items.
     * @return A [Response] indicating the success or failure of the search operation.
     *
     *         - [Response.Success]: Contains a list of [FoodItem] representing the search results.
     *         - [Response.Failure]: Contains an error of type [FetchException] in case of failure.
     */
    override suspend fun invoke(arg: String): Response<List<FoodItem>, FetchException> {
        // Search for food and map the models to UI adapted models
        return foodRepository.search(arg).fold(
            { data -> Response.Success(data.mapNotNull { food -> food.mapToItem() }) },
            { error -> Response.Failure(error) }
        )
    }
}


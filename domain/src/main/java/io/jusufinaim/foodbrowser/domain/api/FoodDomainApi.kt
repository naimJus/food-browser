package io.jusufinaim.foodbrowser.domain.api

import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.domain.di.qualifiers.GetFoodQualifier
import io.jusufinaim.foodbrowser.domain.model.FoodItem
import io.jusufinaim.foodbrowser.domain.usecase.UseCase

/**
 * [FoodDomainApi] is an API exposed from this software layer.
 * It provides other software layers way of interacting/communicating with this layer of the software.
 * All communication will go through the instances exposed from this API
 */
interface FoodDomainApi {

    /**
     * Provides an instance of UseCase<String, Response<List<FoodItem>, FetchException>> to interact with food-related data.
     *
     * @return An instance of ```UseCase<String, Response<List<FoodItem>, FetchException>>```.
     */
    @GetFoodQualifier
    fun getFoodUseCase(): UseCase<String, Response<List<FoodItem>, FetchException>>
}
package io.jusufinaim.foodbrowser.data.api

import io.jusufinaim.foodbrowser.data.repository.FoodRepository

/**
 * [FoodDataApi] is an API exposed from this software layer.
 * It provides other software layers way of interacting/communicating with this layer of the software.
 * All communication will go through the instances exposed from this API
 */
interface FoodDataApi {

    /**
     * Provides an instance of [FoodRepository] to interact with food-related data.
     *
     * @return An instance of [FoodRepository].
     */
    fun foodRepository(): FoodRepository
}
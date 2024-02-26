package io.jusufinaim.foodbrowser.data.model.bridge

/**
 * [FoodBridge] is a data class serving as a bridge between different representations of Food Items from various sources
 * such as network, preference, and database.
 *
 * @property id The unique identifier of the food.
 * @property brand The brand name of the food.
 * @property name The name of the food.
 * @property calories The calorie content of the food.
 * @property portion The portion size of the food.
 */
data class FoodBridge(
    val id: Int? = null,
    val brand: String? = null,
    val name: String? = null,
    val calories: Int? = null,
    val portion: Int? = null
)
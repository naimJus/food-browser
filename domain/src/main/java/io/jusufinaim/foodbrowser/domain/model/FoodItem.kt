package io.jusufinaim.foodbrowser.domain.model


/**
 * [FoodItem] is a data class representing the UI representation of a food item.
 *
 * @property id The unique identifier of the food.
 * @property brand The brand name of the food.
 * @property name The name of the food.
 * @property calories The calorie content of the food.
 * @property portion The portion size of the food.
 */
data class FoodItem(
    val id: Int,
    val brand: String,
    val name: String,
    val calories: Int,
    val portion: Int
)
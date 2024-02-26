package io.jusufinaim.foodbrowser.data.model.remote

/**
 *  Example Response:
 *  ```
 *   {
 *    "id": 0,
 *    "brand": "Journal Communications",
 *    "name": "BBQ Chicken Pizza",
 *    "calories": 119,
 *    "portion": 231
 *  }
 *  ```
 *
 * [RemoteFood] is a data class representing food-related information retrieved from a remote source.
 *
 * @property id The unique identifier of the food.
 * @property brand The brand name of the food.
 * @property name The name of the food.
 * @property calories The calorie content of the food per 100 gram.
 * @property portion The number of grams in 1 portion of the food .
 *
 */
internal data class RemoteFood(
    val id: Int? = null,
    val brand: String? = null,
    val name: String? = null,
    val calories: Int? = null,
    val portion: Int? = null
)
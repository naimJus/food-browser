package io.jusufinaim.foodbrowser.domain.util

import io.jusufinaim.foodbrowser.data.model.bridge.FoodBridge
import io.jusufinaim.foodbrowser.domain.model.FoodItem


/**
 * Map a FoodBridge model to a FoodItem model. The FoodItem model is more suitable for the UI layer representation. If any of the UI related required fields is null the whole mapping process will return null.
 */
internal fun FoodBridge.mapToItem(): FoodItem? {
    return try {
        FoodItem(
            requireNotNull(id) { "Cannot parse food item $this, ID field is null. UI layer needs models with non null ID" },
            requireNotNull(brand) { "Cannot parse food item $this, Brand field is null. UI layer needs models with non null Brand" },
            requireNotNull(name) { "Cannot parse food item $this, Name field is null. UI layer needs models with non null Name" },
            requireNotNull(calories) { "Cannot parse food item $this, Calories field is null. UI layer needs models with non null Calories" },
            requireNotNull(portion) { "Cannot parse food item $this, Portion field is null. UI layer needs models with non null Portion" },
        )
    } catch (e: IllegalArgumentException) {
        null
    }
}
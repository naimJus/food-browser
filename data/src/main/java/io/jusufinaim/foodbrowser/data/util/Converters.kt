package io.jusufinaim.foodbrowser.data.util

import io.jusufinaim.foodbrowser.data.model.bridge.FoodBridge
import io.jusufinaim.foodbrowser.data.model.remote.RemoteFood

internal fun RemoteFood.mapToFoodBridge() = FoodBridge(id, brand, name, calories, portion)

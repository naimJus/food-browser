package io.jusufinaim.foodbrowser.domain.util

import io.jusufinaim.foodbrowser.data.model.bridge.FoodBridge
import io.jusufinaim.foodbrowser.domain.model.FoodItem

fun createValidFoodBridge(): FoodBridge = FoodBridge(
    id = 1,
    brand = "Test Brand",
    name = "Test Food",
    calories = 200,
    portion = 1
)

fun createExpectedFoodItem(): FoodItem = FoodItem(
    id = 1,
    brand = "Test Brand",
    name = "Test Food",
    calories = 200,
    portion = 1
)

val mockFoodItem: FoodBridge = FoodBridge(
    id = 1,
    brand = "Mock Brand",
    name = "Mock Food",
    calories = 200,
    portion = 1
)

val foodBridgeItems: List<FoodBridge> = listOf(
    mockFoodItem,
    FoodBridge(1, "Journal Communications", "BBQ Chicken Pizza", 119, 231),
    FoodBridge(1, "Journal Communications", "Alice Springs Chicken", 134, 264),
    FoodBridge(1, "Starbucks", "Chicken Alfredo", 873, 414),
)

val invalidFoodItem = foodBridgeItems.map { it.copy(name = null) }
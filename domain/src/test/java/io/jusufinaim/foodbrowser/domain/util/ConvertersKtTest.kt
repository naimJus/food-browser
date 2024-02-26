package io.jusufinaim.foodbrowser.domain.util

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ConvertersKtTest {

    @Test
    fun `mapToItem with valid FoodBridge returns FoodItem`() {
        val foodBridge = createValidFoodBridge()

        val result = foodBridge.mapToItem()

        assertEquals(createExpectedFoodItem(), result)
    }

    @Test
    fun `mapToItem with FoodBridge missing ID returns null`() {
        val foodBridge = createValidFoodBridge().copy(id = null)

        val result = foodBridge.mapToItem()

        assertNull(result)
    }

    @Test
    fun `mapToItem with FoodBridge missing Brand returns null`() {
        val foodBridge = createValidFoodBridge().copy(brand = null)

        val result = foodBridge.mapToItem()

        assertNull(result)
    }

    @Test
    fun `mapToItem with FoodBridge missing Name returns null`() {
        val foodBridge = createValidFoodBridge().copy(name = null)

        val result = foodBridge.mapToItem()

        assertNull(result)
    }

    @Test
    fun `mapToItem with FoodBridge missing Calories returns null`() {
        val foodBridge = createValidFoodBridge().copy(calories = null)

        val result = foodBridge.mapToItem()

        assertNull(result)
    }

    @Test
    fun `mapToItem with FoodBridge missing Portion returns null`() {
        val foodBridge = createValidFoodBridge().copy(portion = null)

        val result = foodBridge.mapToItem()

        assertNull(result)
    }

    @Test
    fun `mapToItem with FoodBridge list missing name returns not null list`() {
        val result = invalidFoodItem.mapNotNull { it.mapToItem() }

        assertNotNull(result)
    }

    @Test
    fun `mapToItem with FoodBridge list missing name returns empty list`() {
        val result = invalidFoodItem.mapNotNull { it.mapToItem() }

        assertTrue(result.isEmpty())
    }

}
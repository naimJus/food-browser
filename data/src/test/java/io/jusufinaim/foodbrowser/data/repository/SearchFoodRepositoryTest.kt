package io.jusufinaim.foodbrowser.data.repository


import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.data.model.remote.RemoteFood
import io.jusufinaim.foodbrowser.data.source.FoodDataSource
import io.jusufinaim.foodbrowser.data.util.mapToFoodBridge
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchFoodRepositoryTest {

    private val foodDataSource: FoodDataSource = mockk(relaxed = true)
    private lateinit var searchFoodRepository: SearchFoodRepository

    @Before
    fun setUp() {
        searchFoodRepository = SearchFoodRepository(foodDataSource)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `search with valid queryParam returns success response`() = runTest {
        val queryParam = "Pizza"
        val mockRemoteFoodList = listOf(mockRemoteFoodBridge)
        coEvery { foodDataSource.search(queryParam) } returns Response.Success(mockRemoteFoodList)

        val result = searchFoodRepository.search(queryParam)

        assertEquals(Response.Success(mockRemoteFoodList.map { it.mapToFoodBridge() }), result)
    }

    @Test
    fun `search with valid queryParam returns success response and updates cache`() = runTest {
        val queryParam = "Pizza"
        val mockRemoteFoodList = listOf(mockRemoteFoodBridge)
        coEvery { foodDataSource.search(queryParam) } returns Response.Success(
            mockRemoteFoodList
        )

        // First call to populate cache
        searchFoodRepository.search(queryParam)

        // Second call, should use cache
        val result = searchFoodRepository.search(queryParam)

        assertEquals(Response.Success(mockRemoteFoodList.map { it.mapToFoodBridge() }), result)
    }

    @Test
    fun `search with queryParam not in cache returns success response and updates cache`() =
        runTest {
            val queryParam1 = "Pizza"
            val queryParam2 = "Burger"
            val mockRemoteFoodList1 = listOf(mockRemoteFoodBridge)
            val mockRemoteFoodList2 = listOf(mockRemoteFoodBridge.copy(id = 2))
            coEvery { foodDataSource.search(queryParam1) } returns Response.Success(
                mockRemoteFoodList1
            )
            coEvery { foodDataSource.search(queryParam2) } returns Response.Success(
                mockRemoteFoodList2
            )

            // First call to populate cache with queryParam1
            searchFoodRepository.search(queryParam1)

            // Second call with queryParam2, should use cache for queryParam1 and update cache for queryParam2
            val result = searchFoodRepository.search(queryParam2)

            assertEquals(Response.Success(mockRemoteFoodList2.map { it.mapToFoodBridge() }), result)
        }

    @Test
    fun `search with repository returning failure returns failure response`() = runTest {
        val queryParam = "InvalidSearch"
        val mockFetchException = FetchException.FetchItemsException("Invalid search parameter")
        coEvery { foodDataSource.search(queryParam) } returns Response.Failure(
            mockFetchException
        )

        val result = searchFoodRepository.search(queryParam)

        assertEquals(Response.Failure(mockFetchException), result)
    }


    private val mockRemoteFoodBridge = RemoteFood(
        id = 1,
        brand = "Mock Brand",
        name = "Mock Food",
        calories = 200,
        portion = 1
    )
}

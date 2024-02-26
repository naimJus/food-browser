package io.jusufinaim.foodbrowser.domain.usecase

import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.data.repository.FoodRepository
import io.jusufinaim.foodbrowser.domain.model.FoodItem
import io.jusufinaim.foodbrowser.domain.util.foodBridgeItems
import io.jusufinaim.foodbrowser.domain.util.mapToItem
import io.jusufinaim.foodbrowser.domain.util.mockFoodItem
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetFoodUseCaseTest {

    private val foodRepository: FoodRepository = mockk(relaxed = true)
    private lateinit var getFoodUseCase: GetFoodUseCase

    @Before
    fun setUp() {
        getFoodUseCase = GetFoodUseCase(foodRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke with valid argument returns success response list`() = runTest {
        val searchParam = "Pizza"

        coEvery { foodRepository.search(searchParam) } returns Response.Success(foodBridgeItems)

        val result: Response<List<FoodItem>, Throwable> = getFoodUseCase(searchParam)

        assertEquals(Response.Success(foodBridgeItems.mapNotNull { it.mapToItem() }), result)
    }

    @Test
    fun `invoke with valid argument returns success response`() = runTest {
        val searchParam = "Pizza"
        val mockFoodList = listOf(mockFoodItem)

        coEvery { foodRepository.search(searchParam) } returns Response.Success(mockFoodList)

        val result: Response<List<FoodItem>, Throwable> = getFoodUseCase(searchParam)

        assertEquals(Response.Success(mockFoodList.mapNotNull { it.mapToItem() }), result)
    }

    @Test
    fun `invoke with FetchException FetchItemsException returning failure returns failure response`() =
        runTest {
            val searchParam = "InvalidSearch"
            val mockFetchException = FetchException.FetchItemsException("Invalid search parameter")

            coEvery { (foodRepository.search(searchParam)) } returns Response.Failure(
                mockFetchException
            )

            val result = getFoodUseCase(searchParam)

            assertEquals(Response.Failure(mockFetchException), result)
        }

    @Test
    fun `invoke with FetchException FetchItemsException returns failure response`() = runTest {
        val searchParam = "InvalidSearch"
        coEvery { foodRepository.search(searchParam) } returns Response.Failure(
            FetchException.FetchItemsException(
                "Invalid JSON"
            )
        )
        val result = getFoodUseCase(searchParam)

        assertEquals(Response.Failure(FetchException.FetchItemsException("Invalid JSON")), result)
    }

    @Test
    fun `invoke with FetchException NetworkException returns network failure response`() = runTest {
        val searchParam = "InvalidSearch"

        coEvery { foodRepository.search(searchParam) } returns Response.Failure(FetchException.NetworkException)

        val result = getFoodUseCase(searchParam)

        assertEquals(Response.Failure(FetchException.NetworkException), result)
    }

    @Test
    fun `invoke with FetchException NoItemsFoundException returns failure response with exception message`() =
        runTest {
            val searchParam = "InvalidSearch"

            coEvery { (foodRepository.search(searchParam)) } returns Response.Failure(
                FetchException.NoItemsFoundException(
                    "No item"
                )
            )

            val result = getFoodUseCase(searchParam)

            assertEquals(
                Response.Failure(FetchException.NoItemsFoundException("No item")),
                result
            )
        }
}

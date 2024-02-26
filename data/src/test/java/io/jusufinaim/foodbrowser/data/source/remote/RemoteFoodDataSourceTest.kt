package io.jusufinaim.foodbrowser.data.source.remote

import com.google.gson.JsonParseException
import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.data.model.remote.RemoteFood
import io.jusufinaim.foodbrowser.data.service.FoodService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.UnknownHostException

@RunWith(JUnit4::class)
class RemoteFoodDataSourceTest {
    private val mockFoodService: FoodService = mockk(relaxed = true)
    private lateinit var remoteFoodDataSource: RemoteFoodDataSource

    @Before
    fun setUp() {
        remoteFoodDataSource = RemoteFoodDataSource(mockFoodService)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `search with valid searchParam returns success response`() = runTest {
        val searchParam = "Pizza"
        val mockRemoteFoodList = listOf(mockRemoteFood)
        coEvery { mockFoodService.search(searchParam) } returns mockRemoteFoodList

        val result = remoteFoodDataSource.search(searchParam)

        assertEquals(Response.Success(mockRemoteFoodList), result)
    }

    @Test
    fun `search with JsonParseException returns failure response`() = runTest {
        val searchParam = "JsonParseException"
        coEvery { mockFoodService.search(searchParam) } throws JsonParseException("Invalid JSON")

        val result = remoteFoodDataSource.search(searchParam)

        result.fold(
            { _ -> assertTrue("Should be Response.Failure", false) },
            { _ -> assertTrue("Is Response.Failure", true) }
        )
    }

    @Test
    fun `search with JsonParseException returns failure response with FetchItemsException`() =
        runTest {
            val searchParam = "JsonParseException"
            coEvery { mockFoodService.search(searchParam) } throws JsonParseException("Invalid JSON")

            val result = remoteFoodDataSource.search(searchParam)

            result.fold(
                { _ -> assertTrue("Should be Response.Failure", false) },
                { failure: FetchException ->
                    assertTrue(failure is FetchException.FetchItemsException)
                }
            )
        }

    @Test
    fun `search with UnknownHostException returns network failure response`() = runTest {
        val searchParam = "UnknownHostException"
        coEvery { mockFoodService.search(searchParam) } throws UnknownHostException()

        val result = remoteFoodDataSource.search(searchParam)

        assertEquals(Response.Failure(FetchException.NetworkException), result)
    }

    @Test
    fun `search with general exception returns failure response with exception message`() =
        runTest {
            val searchParam = "GeneralException"
            coEvery { mockFoodService.search(searchParam) } throws Exception("General exception")

            val result = remoteFoodDataSource.search(searchParam)

            assertEquals(
                Response.Failure(FetchException.FetchItemsException("General exception")),
                result
            )
        }

    @Test
    fun `search with valid searchParam invokes foodService search`() = runTest {
        val searchParam = "Pizza"
        val mockRemoteFoodList = listOf(mockRemoteFood)
        coEvery { mockFoodService.search(searchParam) } returns mockRemoteFoodList

        remoteFoodDataSource.search(searchParam)

        coVerify { mockFoodService.search(searchParam) }
    }

    private val mockRemoteFood = RemoteFood(
        id = 1,
        brand = "Mock Brand",
        name = "Mock Food",
        calories = 200,
        portion = 1
    )
}
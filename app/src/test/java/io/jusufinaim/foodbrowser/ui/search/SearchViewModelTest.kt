package io.jusufinaim.foodbrowser.ui.search

import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.domain.model.FoodItem
import io.jusufinaim.foodbrowser.domain.usecase.UseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SearchViewModelTest {

    private val mockGetFoodUseCase: UseCase<String, Response<List<FoodItem>, FetchException>> = mockk()
    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        searchViewModel =
            SearchViewModel(Dispatchers.Unconfined, Dispatchers.Unconfined, mockGetFoodUseCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `search with null queryParam sets uiStateFlow to Initial`() {
        searchViewModel.search(null)

        assertEquals(SearchUiState.Initial, searchViewModel.uiStateFlow.value)
    }

    @Test
    fun `search with empty queryParam sets uiStateFlow to Initial`() {
        searchViewModel.search("")

        assertEquals(SearchUiState.Initial, searchViewModel.uiStateFlow.value)
    }

    @Test
    fun `search with Pizza returning empty list sets uiStateFlow to Error`() = runTest {
        val queryParam = "Pizza"
        val mockResponse: Response<List<FoodItem>, FetchException> = Response.Success(emptyList())

        coEvery { mockGetFoodUseCase(queryParam) } returns mockResponse

        searchViewModel.search(queryParam)

        assertEquals(
            SearchUiState.Error(FetchException.NoItemsFoundException("Sorry, no items found with the name: $queryParam.\nTry searching for another food.")),
            searchViewModel.uiStateFlow.value
        )
    }

    @Test
    fun `search with Pizza returning non-empty list sets uiStateFlow to Success`() =
        runTest {
            val queryParam = "Pizza"
            val mockResponse: Response<List<FoodItem>, FetchException> =
                Response.Success(listOf(mockFoodItem))
            coEvery { mockGetFoodUseCase(queryParam) } returns mockResponse

            searchViewModel.search(queryParam)

            assertEquals(
                SearchUiState.Success(false, listOf(mockFoodItem)),
                searchViewModel.uiStateFlow.value
            )
        }

    @Test
    fun `search with InvalidSearch returning failure sets uiStateFlow to Error`() = runTest {
        val queryParam = "InvalidSearch"
        val mockFetchException = FetchException.FetchItemsException("Invalid search parameter")
        val mockResponse: Response<List<FoodItem>, FetchException> =
            Response.Failure(mockFetchException)
        coEvery { mockGetFoodUseCase(queryParam) } returns mockResponse

        searchViewModel.search(queryParam)

        assertEquals(SearchUiState.Error(mockFetchException), searchViewModel.uiStateFlow.value)
    }

    @Test
    fun `search with GeneralException throwing general exception sets uiStateFlow to Error`() =
        runTest {
            val queryParam = "GeneralException"
            coEvery { mockGetFoodUseCase(queryParam) } throws Exception("General exception")

            searchViewModel.search(queryParam)

            assertEquals(
                SearchUiState.Error(FetchException.UnknownException()),
                searchViewModel.uiStateFlow.value
            )
        }

    private val mockFoodItem = FoodItem(
        id = 1,
        brand = "Mock Brand",
        name = "Mock Food",
        calories = 200,
        portion = 1
    )
}

package io.jusufinaim.foodbrowser.ui.search

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.jusufinaim.foodbrowser.R
import io.jusufinaim.foodbrowser.common.util.findCause
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.domain.model.FoodItem
import io.jusufinaim.foodbrowser.ui.theme.FoodBrowserTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(modifier: Modifier = Modifier, viewModel: SearchViewModel) {
    val _state by viewModel.uiStateFlow.collectAsState()
    val state = _state
    var clickedItem: FoodItem? by remember { mutableStateOf(null) }

    val onFoodClicked = { item: FoodItem ->
        clickedItem = item
    }

    clickedItem?.let {
        ToastMessage(message = it.name)
        clickedItem = null
    }

    Column(modifier) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) { queryParam ->
            viewModel.search(queryParam)
        }

        when (state) {
            is SearchUiState.Success -> FoodsListView(
                modifier.fillMaxSize(),
                state = state,
                onFoodClicked = onFoodClicked
            )

            is SearchUiState.Error -> ErrorView(
                Modifier.fillMaxSize(),
                state.error
            )

            SearchUiState.Initial -> InitialView(
                Modifier
                    .fillMaxSize()
                    .padding(12.dp))
        }
    }
}

@Composable
private fun SearchBar(modifier: Modifier, onSearch: (String?) -> Unit) {
    var text: String? by rememberSaveable { mutableStateOf(null) }
    LaunchedEffect(text) {
        launch {
            delay(700)
            onSearch(text)
        }
    }
    TextField(
        modifier = modifier,
        onValueChange = { newText -> text = newText },
        value = text ?: "",
        placeholder = { Text(text = stringResource(id = R.string.search)) },
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search),
            )
        }
    )
}


@Composable
fun FoodsListView(
    modifier: Modifier = Modifier,
    state: SearchUiState.Success,
    onFoodClicked: (FoodItem) -> Unit
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        LazyColumn {
            items(items = state.data) { item ->
                ElevatedCard(modifier = Modifier
                    .padding(12.dp)
                    .clickable { onFoodClicked(item) }) {
                    FoodItem(item = item)
                }
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                Modifier
                    .size(60.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ErrorView(modifier: Modifier = Modifier, error: FetchException) {
    val text: String = when (error) {
        FetchException.NetworkException -> stringResource(id = R.string.network_error)
        is FetchException.UnknownException -> error.findCause<Throwable>()?.message?.let {
            stringResource(
                R.string.default_error_arg
            )
        } ?: stringResource(id = R.string.default_error)

        is FetchException.FetchItemsException -> stringResource(
            id = R.string.error_while_fetching_items,
            error.message
        )

        is FetchException.NoItemsFoundException -> error.message

    }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun InitialView(modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(id = R.string.initial_search_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun FoodItem(item: FoodItem) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp)) {
        Label(
            labelRes = R.string.name_arg,
            value = item.name,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.width(8.dp))
        Label(
            labelRes = R.string.brand_arg,
            value = item.brand,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(4.dp))
        Label(
            labelRes = R.string.calories_arg,
            value = item.calories,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(4.dp))
        Label(
            labelRes = R.string.portion_arg,
            value = item.portion,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(4.dp))
    }
}

@Composable
fun Label(@StringRes labelRes: Int, value: Any, style: TextStyle) {
    Text(
        text = stringResource(id = labelRes, value),
        style = style,
    )
}

@Composable
fun ToastMessage(context: Context = LocalContext.current, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(
    name = "dark_mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun FoodsListViewPreview() {
    FoodBrowserTheme {
        Surface {
            FoodsListView(
                modifier = Modifier.fillMaxSize(),
                state = SearchUiState.Success(
                    isLoading = false,
                    data = listOf(
                        FoodItem(1, "Journal Communications", "BBQ Chicken Pizza", 119, 231),
                        FoodItem(1, "Journal Communications", "Alice Springs Chicken", 134, 264),
                        FoodItem(1, "Starbucks", "Chicken Alfredo", 873, 414),
                    )
                ),
                onFoodClicked = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(
    name = "dark_mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun FoodsListWithProgressViewPreview() {
    FoodBrowserTheme {
        Surface {
            FoodsListView(
                modifier = Modifier.fillMaxSize(),
                state = SearchUiState.Success(
                    isLoading = true,
                    data = listOf(
                        FoodItem(1, "Journal Communications", "BBQ Chicken Pizza", 119, 231),
                        FoodItem(1, "Journal Communications", "Alice Springs Chicken", 134, 264),
                        FoodItem(1, "Starbucks", "Chicken Alfredo", 873, 414),
                    )
                ),
                onFoodClicked = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(
    name = "dark_mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ErrorPreview() {
    FoodBrowserTheme {
        Surface {
            ErrorView(
                Modifier.fillMaxSize(),
                FetchException.NetworkException
            )
        }
    }
}

@Composable
@Preview
fun ToastPreview() {
    FoodBrowserTheme {
        Surface {
            ToastMessage(message = "Hawaiian Pizza")
        }
    }
}
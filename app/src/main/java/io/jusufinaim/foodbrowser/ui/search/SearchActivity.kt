package io.jusufinaim.foodbrowser.ui.search

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import io.jusufinaim.foodbrowser.ui.theme.FoodBrowserTheme
import io.jusufinaim.foodbrowser.util.DiComposeActivity
import javax.inject.Inject

class SearchActivity : DiComposeActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: SearchViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodBrowserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchScreen(viewModel = viewModel)
                }
            }
        }
    }
}
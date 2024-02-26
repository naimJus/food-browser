package io.jusufinaim.foodbrowser.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.jusufinaim.foodbrowser.di.ViewModelFactory
import io.jusufinaim.foodbrowser.di.provider.ViewModelKey
import io.jusufinaim.foodbrowser.di.scope.ApplicationScope
import io.jusufinaim.foodbrowser.ui.search.SearchViewModel

@Module
internal interface ViewModelModule {
    @Binds
    @ApplicationScope
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel
}
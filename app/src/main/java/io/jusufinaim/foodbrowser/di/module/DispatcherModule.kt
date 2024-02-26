package io.jusufinaim.foodbrowser.di.module

import dagger.Module
import dagger.Provides
import io.jusufinaim.foodbrowser.di.qualifier.IoDispatcher
import io.jusufinaim.foodbrowser.di.qualifier.MainDispatcher
import io.jusufinaim.foodbrowser.di.qualifier.TestDispatcher
import io.jusufinaim.foodbrowser.di.scope.ApplicationScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class DispatcherModule {

    @Provides
    @ApplicationScope
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineContext = Dispatchers.IO

    @Provides
    @ApplicationScope
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineContext = Dispatchers.Main

    @Provides
    @ApplicationScope
    @TestDispatcher
    fun provideUnconfined(): CoroutineContext = Dispatchers.Unconfined
}
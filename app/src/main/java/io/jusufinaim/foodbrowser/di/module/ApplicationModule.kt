package io.jusufinaim.foodbrowser.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.jusufinaim.foodbrowser.di.scope.ApplicationScope

@Module
class ApplicationModule(private val application: Application) {
    @Provides
    @ApplicationScope
    internal fun provideContext(): Context = application

    @Provides
    @ApplicationScope
    internal fun provideApplication(): Application = application
}
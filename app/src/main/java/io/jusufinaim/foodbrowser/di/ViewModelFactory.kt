package io.jusufinaim.foodbrowser.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.jusufinaim.foodbrowser.di.scope.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

/**
 * ViewModelFactory which uses Dagger to create the instances.
 */
@ApplicationScope
internal class ViewModelFactory @Inject constructor(private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val found = creators.entries.find { modelClass.isAssignableFrom(it.key) }
        val creator =
            found?.value ?: throw IllegalArgumentException("Unknown model class $modelClass")

        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
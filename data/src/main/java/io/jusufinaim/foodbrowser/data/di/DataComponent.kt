package io.jusufinaim.foodbrowser.data.di

import dagger.BindsInstance
import dagger.Component
import io.jusufinaim.foodbrowser.data.api.FoodDataApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [DataModule::class])
@Singleton
interface DataComponent : FoodDataApi {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance retrofit: Retrofit): DataComponent
    }
}
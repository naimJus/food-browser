package io.jusufinaim.foodbrowser.domain.di

import dagger.BindsInstance
import dagger.Component
import io.jusufinaim.foodbrowser.data.repository.FoodRepository
import io.jusufinaim.foodbrowser.domain.api.FoodDomainApi
import javax.inject.Singleton

@Component(modules = [DomainModule::class])
@Singleton
interface DomainComponent : FoodDomainApi {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance foodRepository: FoodRepository): DomainComponent
    }
}
package io.jusufinaim.foodbrowser.domain.di

import dagger.Module
import dagger.Provides
import io.jusufinaim.foodbrowser.common.model.Response
import io.jusufinaim.foodbrowser.data.api.FoodDataApi
import io.jusufinaim.foodbrowser.data.model.exception.FetchException
import io.jusufinaim.foodbrowser.data.repository.FoodRepository
import io.jusufinaim.foodbrowser.domain.di.qualifiers.GetFoodQualifier
import io.jusufinaim.foodbrowser.domain.model.FoodItem
import io.jusufinaim.foodbrowser.domain.usecase.GetFoodUseCase
import io.jusufinaim.foodbrowser.domain.usecase.UseCase

/**
 * The domain should hide the actual (concrete) implementation of the UseCases and expose Interfaces and Qualifiers
 */
@Module
internal class DomainModule {

    @Provides
    @GetFoodQualifier
    fun providesGetFoodUseCase(foodRepository: FoodRepository): UseCase<String, Response<List<FoodItem>, FetchException>> = GetFoodUseCase(foodRepository)
}
package io.jusufinaim.foodbrowser.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.jusufinaim.foodbrowser.data.repository.FoodRepository
import io.jusufinaim.foodbrowser.data.repository.SearchFoodRepository
import io.jusufinaim.foodbrowser.data.service.FoodService
import io.jusufinaim.foodbrowser.data.source.FoodDataSource
import io.jusufinaim.foodbrowser.data.source.remote.RemoteFoodDataSource
import retrofit2.Retrofit

@Module
internal abstract class DataModule {

    @Binds
    internal abstract fun bindRepository(repository: SearchFoodRepository): FoodRepository

    @Binds
    internal abstract fun bindRemoteDataSource(dataSource: RemoteFoodDataSource): FoodDataSource

    internal companion object {
        @Provides
        @JvmStatic
        internal fun provideFoodsService(retrofit: Retrofit): FoodService {
            return retrofit.create(FoodService::class.java)
        }
    }
}
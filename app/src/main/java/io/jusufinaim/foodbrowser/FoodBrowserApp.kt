package io.jusufinaim.foodbrowser

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.jusufinaim.foodbrowser.data.di.DaggerDataComponent
import io.jusufinaim.foodbrowser.di.component.DaggerApplicationComponent
import io.jusufinaim.foodbrowser.di.component.DaggerNetworkComponent
import io.jusufinaim.foodbrowser.di.module.ApplicationModule
import io.jusufinaim.foodbrowser.domain.di.DaggerDomainComponent

class FoodBrowserApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val networkComponent = DaggerNetworkComponent.factory().create()
        val dataComponent = DaggerDataComponent.factory().create(networkComponent.provideRetrofit())
        val domainComponent = DaggerDomainComponent.factory().create(dataComponent.foodRepository())
        return DaggerApplicationComponent.factory().create(
            ApplicationModule(this),
            networkComponent,
            domainComponent,
            dataComponent,
            this
        )
    }
}
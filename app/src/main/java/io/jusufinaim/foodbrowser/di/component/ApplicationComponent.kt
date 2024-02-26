package io.jusufinaim.foodbrowser.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.jusufinaim.foodbrowser.FoodBrowserApp
import io.jusufinaim.foodbrowser.data.di.DataComponent
import io.jusufinaim.foodbrowser.di.module.ActivityBindingModule
import io.jusufinaim.foodbrowser.di.module.ApplicationModule
import io.jusufinaim.foodbrowser.di.module.DispatcherModule
import io.jusufinaim.foodbrowser.di.module.ViewModelModule
import io.jusufinaim.foodbrowser.di.scope.ApplicationScope
import io.jusufinaim.foodbrowser.domain.di.DomainComponent

@Component(
    dependencies = [
        NetworkComponent::class,
        DataComponent::class,
        DomainComponent::class,
    ],
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        DispatcherModule::class,
        ViewModelModule::class,
        ActivityBindingModule::class
    ]
)
@ApplicationScope
interface ApplicationComponent : AndroidInjector<FoodBrowserApp> {

    @Component.Factory
    interface Factory {
        fun create(
            applicationModule: ApplicationModule,
            networkComponent: NetworkComponent,
            domainComponent: DomainComponent,
            dataComponent: DataComponent,
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}
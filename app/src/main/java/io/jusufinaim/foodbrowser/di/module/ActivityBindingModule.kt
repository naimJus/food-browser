package io.jusufinaim.foodbrowser.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.jusufinaim.foodbrowser.di.component.ApplicationComponent
import io.jusufinaim.foodbrowser.di.scope.ActivityScope
import io.jusufinaim.foodbrowser.ui.search.SearchActivity

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module
 * ActivityBindingModule is on, in our case that will be [ApplicationComponent]. You never
 * need to tell [ApplicationComponent] that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that [ApplicationComponent] exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the
 * specified modules and be aware of a scope annotation [ActivityScope].
 * When Dagger.Android annotation processor runs it will create 2 subcomponents for us.
 */
@Module
interface ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector
    fun contributeSearchActivity(): SearchActivity
}

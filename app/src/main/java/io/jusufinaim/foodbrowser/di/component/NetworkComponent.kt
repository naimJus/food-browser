package io.jusufinaim.foodbrowser.di.component

import com.google.gson.Gson
import dagger.Component
import io.jusufinaim.foodbrowser.di.module.NetworkModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface NetworkComponent {

    @Singleton
    fun provideOkHttpClient(): OkHttpClient

    @Singleton
    fun provideRetrofit(): Retrofit

    @Singleton
    fun provideGson(): Gson

    @Component.Factory
    interface Factory {
        fun create(): NetworkComponent
    }
}

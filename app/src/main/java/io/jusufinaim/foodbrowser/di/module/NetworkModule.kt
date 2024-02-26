package io.jusufinaim.foodbrowser.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.jusufinaim.foodbrowser.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {
    @Provides
    @Singleton
    internal fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()

    @Provides
    @Singleton
    internal fun provideGson() = Gson()

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.FOOD_WEB_SERVER_URL)
            .build()
}
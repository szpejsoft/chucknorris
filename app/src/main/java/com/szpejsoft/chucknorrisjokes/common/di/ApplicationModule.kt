package com.szpejsoft.chucknorrisjokes.common.di

import com.squareup.moshi.Moshi
import com.szpejsoft.chucknorrisjokes.common.screentitle.AppBarTitleManager
import com.szpejsoft.chucknorrisjokes.networking.ChuckNorrisApi
import com.szpejsoft.chucknorrisjokes.networking.category.CategoriesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun retrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

        val moshi = Moshi.Builder()
            .add(CategoriesAdapter)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun chuckNorrisApi(retrofit: Retrofit): ChuckNorrisApi {
        return retrofit.create(ChuckNorrisApi::class.java)
    }

    @Provides
    @Singleton
    fun appBarTitleManager(): AppBarTitleManager = AppBarTitleManager()

}

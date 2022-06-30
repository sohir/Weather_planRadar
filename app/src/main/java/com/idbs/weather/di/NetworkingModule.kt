package com.idbs.weather.di

import com.idbs.weather.BuildConfig
import com.idbs.weather.network.ApiServices
import com.idbs.weather.utilit.UrlsConstants.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @Singleton
    fun provideLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Provides
    @Singleton
    fun provideOKHttpClient(logger: HttpLoggingInterceptor):
            OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.addInterceptor { chain ->
                val request: Request = chain.request().newBuilder().addHeader("X-Auth-Token", "6d0c1627177943118d15af8b49792aed").build()
                chain.proceed(request)
            }
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(logger)
        }
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory{
        return MoshiConverterFactory.create(
            Moshi.Builder().add(KotlinJsonAdapterFactory())
                .build()
        )
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,converterFactory:Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(converterFactory)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }
}
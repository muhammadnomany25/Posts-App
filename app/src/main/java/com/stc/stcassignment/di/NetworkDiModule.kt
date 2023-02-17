package com.stc.stcassignment.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stc.stcassignment.data.network.ArticleApi
import com.stc.stcassignment.domain.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDiModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideArticleApiService(retrofitBuilder: Retrofit.Builder): ArticleApi {
        return retrofitBuilder.build().create(ArticleApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val headerInterceptor = Interceptor { chain ->
            val request = chain.request()
            val builder: Request.Builder = request.newBuilder()
            builder.addHeader("x-api-key", Constants.API_KEY)
            chain.proceed(builder.build())
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(headerInterceptor)
            .build()
    }


}




















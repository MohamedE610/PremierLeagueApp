package com.example.premierleagueapp.core.data.source.remote

import android.annotation.SuppressLint
import com.example.premierleagueapp.BuildConfig
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.RxErrorHandlingCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGenerator {


    @SuppressLint("LogNotTimber")
    fun <S> createService(
        serviceClass: Class<S>
    ): S {

        val baseURL = BuildConfig.BASE_URL

        val httpClient = OkHttpClient.Builder()
        val builder = Retrofit.Builder()

        httpClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            httpClient.addInterceptor(logging)
        }

        builder.baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient().withNullSerialization())
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())

        val headersInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url().newBuilder()
                .build()
            val request = chain.request().newBuilder()
                .addHeader(X_AUTH_TOKEN, BuildConfig.API_KEY)
                .url(url)
                .build()
            chain.proceed(request)
        }

        httpClient.addInterceptor(headersInterceptor)

        builder.client(httpClient.build())
        val retrofit = builder.build()
        return retrofit.create(serviceClass)
    }

    companion object {
        private const val X_AUTH_TOKEN = "X-Auth-Token"
    }
}
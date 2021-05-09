package com.tistory.realapril.mybooks.di

import com.tistory.realapril.mybooks.remote.BookApiSource
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

val netWorkModule = module {
    single { createOkHttp() }

    single {
        createWebService<BookApiSource>(get(), "https://www.googleapis.com/")
    }

}


fun createOkHttp() : OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String) : T{

    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}
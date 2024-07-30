package com.example.books.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.books.BookApplication
import com.example.books.common.Constants
import com.example.books.data.remote.BooksApi
import com.example.books.data.repository.BookRepositoryImpl
import com.example.books.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBookRepository(sharedPrefs: SharedPreferences,api: BooksApi): BookRepository {
        return BookRepositoryImpl(sharedPrefs,api)
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
}
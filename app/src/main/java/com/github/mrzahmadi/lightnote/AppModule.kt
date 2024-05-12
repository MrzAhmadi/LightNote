package com.github.mrzahmadi.lightnote

import android.content.Context
import com.github.mrzahmadi.lightnote.data.api.ApiService
import com.github.mrzahmadi.lightnote.data.api.ApiServiceImpl
import com.github.mrzahmadi.lightnote.data.api.provider.OkHttpClientProvider
import com.github.mrzahmadi.lightnote.data.api.provider.RetrofitProvider
import com.github.mrzahmadi.lightnote.data.db.AppDatabase
import com.github.mrzahmadi.lightnote.data.db.DatabaseBuilder
import com.github.mrzahmadi.lightnote.data.db.dao.NoteDao
import com.github.mrzahmadi.lightnote.data.repository.ApiRepository
import com.github.mrzahmadi.lightnote.data.repository.NoteRepository
import com.github.mrzahmadi.lightnote.data.repository.SharedPreferencesRepository
import com.github.mrzahmadi.lightnote.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): AppDatabase {
        return DatabaseBuilder.getInstance(context)
    }

    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClientProvider.createOkHttpClient(context)
    }

    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return RetrofitProvider.createRetrofit(
            baseUrl = BASE_URL,
            gsonConverterFactory = gsonConverterFactory,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideApiServiceImpl(apiService: ApiService): ApiServiceImpl {
        return ApiServiceImpl(apiService)
    }

    @Provides
    fun provideApiRepository(apiService: ApiService): ApiRepository {
        return ApiRepository(apiService)
    }

    @Provides
    fun provideSharedPreferencesRepository(@ApplicationContext context: Context): SharedPreferencesRepository {
        return SharedPreferencesRepository(context)
    }

}
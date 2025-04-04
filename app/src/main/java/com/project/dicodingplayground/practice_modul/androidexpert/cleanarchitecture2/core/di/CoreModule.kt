package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.di

import androidx.room.Room
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.TourismRepository
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.local.LocalDataSource
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.local.room.TourismDatabase
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.RemoteDataSource
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.network.ApiService
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.repository.ITourismRepository
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.usecase.TourismInteractor
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.usecase.TourismUseCase
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.utils.AppExecutors
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.detail.DetailTourismViewModel
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.favorite.FavoriteViewModel
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<TourismDatabase>().tourismDao()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            TourismDatabase::class.java,
            "Tourism.db")
            .fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tourism-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<ITourismRepository> { TourismRepository(get(), get(), get()) }
}

val useCaseModule = module {
    factory<TourismUseCase> { TourismInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailTourismViewModel(get()) }
}
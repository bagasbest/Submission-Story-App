package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data


import android.util.Log
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.local.LocalDataSource
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.RemoteDataSource
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.network.ApiResponse
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.data.source.remote.response.TourismResponse
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.model.Tourism
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.domain.repository.ITourismRepository
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.utils.AppExecutors
import com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.core.utils.DataMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

class TourismRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): ITourismRepository {
    companion object {
        @Volatile
        private var instance: TourismRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): TourismRepository =
            instance ?: synchronized(this) {
                instance ?: TourismRepository(remoteDataSource, localDataSource, appExecutors)
            }
    }

    override fun getAllTourism(): Flowable<Resource<List<Tourism>>> =
        object : NetworkBoundResource<List<Tourism>, List<TourismResponse>>() {
            override fun loadFromDB(): Flowable<List<Tourism>> {
                return localDataSource.getAllTourism().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Tourism>?): Boolean =
//                data.isNullOrEmpty() // mengambil data dari internet hanya jika data di database kosong
                 true // ganti dengan true jika ingin selalu mengambil data dari internet

            override fun createCall(): Flowable<ApiResponse<List<TourismResponse>>> =
                remoteDataSource.getAllTourism()

            override fun saveCallResult(data: List<TourismResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertTourism(tourismList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()

    override fun getFavoriteTourism(): Flowable<List<Tourism>> {
        return localDataSource.getFavoriteTourism().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(tourismEntity, state)}
    }
}

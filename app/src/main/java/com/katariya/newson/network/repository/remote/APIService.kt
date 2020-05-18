package com.katariya.newson.network.repository.remote

import com.katariya.newson.network.model.NewsInfoData
import com.katariya.newson.network.repository.remote.APIURL.Companion.HEADLINES
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET(HEADLINES)
    fun getNews(@Query("country") countryCode:String="in", @Query("apiKey") apiKey:String=""): Observable<Response<NewsInfoData>>
}
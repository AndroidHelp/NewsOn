package com.katariya.newson.network.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katariya.newson.module.NewsOnApplication
import com.katariya.newson.network.di.APIComponent
import com.katariya.newson.network.repository.RetrofitRepository
import javax.inject.Inject

class NewsViewModelFactory : ViewModelProvider.Factory {
    lateinit var apiComponent: APIComponent
    @Inject
    lateinit var retrofitRepository: RetrofitRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //   initDaggerComponent()
        var apiComponent :APIComponent =  NewsOnApplication.apiComponent
        apiComponent.inject(this)
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
/*
    fun initDaggerComponent(){
        apiComponent =   DaggerAPIComponent
            .builder()
            .aPIModule(APIModule(APIURL.BASE_URL))
            .build()
        apiComponent.inject(this)
    }*/
}
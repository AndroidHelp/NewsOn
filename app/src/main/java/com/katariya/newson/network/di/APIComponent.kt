package com.katariya.newson.network.di

import com.katariya.newson.module.AppModule
import com.katariya.newson.network.repository.RetrofitRepository
import com.katariya.newson.network.repository.local.AppDatabase
import com.katariya.newson.view.list.NewsFragment
import com.katariya.newson.network.viewmodel.NewsViewModel
import com.katariya.newson.network.viewmodel.NewsViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,APIModule::class])
interface APIComponent {
    fun inject(retrofitRepository: RetrofitRepository)
    fun inject(retroViewModel: NewsViewModel)
    fun inject(newsFragment: NewsFragment)
    fun inject(retroViewModelFactory: NewsViewModelFactory)
    fun inject(appDatabase: AppDatabase)
}
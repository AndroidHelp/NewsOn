package com.katariya.newson.network.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.katariya.newson.module.NewsOnApplication
import com.katariya.newson.network.di.APIComponent
import com.katariya.newson.network.model.Article
import com.katariya.newson.network.repository.local.AppDatabase
import com.katariya.newson.network.repository.remote.APIService
import com.katariya.newson.utils.getDate
import com.katariya.newson.utils.toArrayList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitRepository {
    private var responseData: MutableLiveData<ArrayList<Article>> = MutableLiveData()
    private var loadingData: MutableLiveData<Boolean> = MutableLiveData()
    private var errorData: MutableLiveData<String> = MutableLiveData()

    val responseLiveData: LiveData<ArrayList<Article>> = responseData
    val loadingLiveData: LiveData<Boolean> = loadingData
    val errorLiveData: LiveData<String> = errorData

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var appDatabase: AppDatabase

    init {
        var apiComponent: APIComponent = NewsOnApplication.apiComponent
        apiComponent.inject(this)
    }


    fun fetchPostInfoList() {
        GlobalScope.launch(Dispatchers.IO) {
            val article = appDatabase.newsDao().lastInserted()
            println("article $article")
            withContext(Dispatchers.Main) {
                if (article != null) {
                    val tsLong = System.currentTimeMillis() / 1000
                    val diffTime = tsLong - article.lastInsertedTimestamp
                    val diffInMin: Long = TimeUnit.SECONDS.toMinutes(diffTime)

                    println("time article ${getDate(article.lastInsertedTimestamp)}")
                    println("time current ${getDate(tsLong)}")
                    Log.e("Tag", "Formatted Date" + diffInMin)
                    if (diffInMin > 15) getDataFromRemote()
                    else responseData.value = appDatabase.newsDao().getAll().toArrayList()
                } else {
                    getDataFromRemote()
                }
            }
        }
    }

    private fun getDataFromRemote() {
        var apiService: APIService = retrofit.create(
            APIService::class.java)
        loadingData.value = true
        apiService.getNews().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadingData.value = false
                if (it.isSuccessful) {
                    if (it.body() != null) {
                        for (article in it.body()!!.articles!!) {
                            article.providerName = article.source!!.sourceName!!
                            article.lastInsertedTimestamp = System.currentTimeMillis() / 1000
                        }
                        GlobalScope.launch {
                            appDatabase.newsDao().insertAll(it.body()!!.articles!!)
                            withContext(Dispatchers.Main) {
                                responseData.value = appDatabase.newsDao().getAll().toArrayList()
                            }
                        }
                    } else errorData.value = it.message()
                } else errorData.value = it.message()
            }, {
                loadingData.value = false
                errorData.value = it.localizedMessage
            })
    }
}
package com.katariya.newson.network.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katariya.newson.network.model.Article
import com.katariya.newson.network.model.NewsInfoData
import com.katariya.newson.network.repository.RetrofitRepository
import kotlinx.coroutines.launch

class NewsViewModel(retrofitRepository: RetrofitRepository): ViewModel() {

    var retrofitRepository:RetrofitRepository = retrofitRepository
    var responseLiveData: LiveData<ArrayList<Article>> = MutableLiveData()
    var loadingLiveData: LiveData<Boolean> = MutableLiveData()
    var errorLiveData: LiveData<String> = MutableLiveData()

    init {
        fetchNewsFromRepository()
    }

    fun fetchNewsFromRepository(){
        viewModelScope.launch{
            retrofitRepository.fetchPostInfoList()
            responseLiveData =  retrofitRepository.responseLiveData
            loadingLiveData = retrofitRepository.loadingLiveData
            errorLiveData = retrofitRepository.errorLiveData
        }
    }


}
package com.katariya.newson.module

import android.app.Application
import android.content.Context
import com.katariya.newson.network.di.APIComponent
import com.katariya.newson.network.di.APIModule
import com.katariya.newson.network.di.DaggerAPIComponent
import com.katariya.newson.network.repository.remote.APIURL


class NewsOnApplication : Application() {

    companion object {
        var ctx: Context? = null
        lateinit var apiComponent: APIComponent
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        apiComponent = initDaggerComponent()
    }

    private fun initDaggerComponent(): APIComponent {
        apiComponent = DaggerAPIComponent
            .builder()
            .aPIModule(APIModule(APIURL.BASE_URL, applicationContext))
            .build()
        return apiComponent

    }

}
package com.katariya.newson.module

import dagger.Module
import dagger.Provides

@Module
class AppModule constructor(private var myRetroApplication: NewsOnApplication){

    @Provides
    fun provideMyRetroApplication():NewsOnApplication{
        return myRetroApplication
    }
}
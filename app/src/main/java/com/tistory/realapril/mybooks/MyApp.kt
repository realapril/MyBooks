package com.tistory.realapril.mybooks

import android.app.Application
import com.tistory.realapril.mybooks.di.netWorkModule
import com.tistory.realapril.mybooks.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            //modules(listOf(viewModelModule, netWorkModule, appModule))
            modules(listOf(viewModelModule, netWorkModule))
        }
    }
}
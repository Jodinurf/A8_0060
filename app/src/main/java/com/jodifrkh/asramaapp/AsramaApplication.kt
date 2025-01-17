package com.jodifrkh.asramaapp

import android.app.Application
import com.jodifrkh.asramaapp.data.di.AppContainer
import com.jodifrkh.asramaapp.data.di.AsramaContainer

class AsramaApplication : Application() {
    lateinit var container : AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AsramaContainer()
    }
}
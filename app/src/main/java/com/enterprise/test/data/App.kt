package com.enterprise.test.data

import android.app.Application
import com.enterprise.test.di.Component
import com.enterprise.test.di.DaggerComponent

class App : Application() {
    lateinit var component: Component

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        component =
            DaggerComponent
                .builder()
                .build()
    }

}
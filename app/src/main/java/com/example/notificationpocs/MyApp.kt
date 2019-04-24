package com.example.notificationpocs

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
Created by kiranb on 24/4/19
 */
class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}
package com.rong360.msm.api

import android.support.v4.app.Fragment
import android.view.View

interface IModule {

    fun getModuleView(): HashMap<String, Class<out View>>

    fun getModuleService(): HashMap<String, Class<*>>

    fun getModuleFragment(): HashMap<String, Class<out Fragment>>
}
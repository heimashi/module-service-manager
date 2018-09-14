package com.rong360.msm.api

import android.view.View

interface IModule {

    fun getModuleView(): HashMap<String, Class<out View>>

    fun getModuleService(): HashMap<String, Class<*>>
}
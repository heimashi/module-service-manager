package com.rong360.msm.api

import android.content.Context
import android.view.View

class ModuleServiceManager {

    private val serviceMap = hashMapOf<String, Class<*>>()

    private val viewMap = hashMapOf<String, Class<out View>>()

    private object SingletonHolder {
        internal var instance = ModuleServiceManager()
    }

    fun loadService(serviceRegisterName: String): Any? {
        val output = serviceMap[serviceRegisterName]
        output ?: return null
        return output.newInstance()
    }

    fun loadModuleService(serviceRegisterName: String): IModuleService? {
        val output = serviceMap[serviceRegisterName]
        output ?: return null
        if (output is IModuleService) {
            return output.newInstance() as IModuleService
        }
        return null
    }

    fun loadServiceClass(serviceRegisterName: String): Class<*>? {
        return serviceMap[serviceRegisterName]
    }

    fun addService(serviceRegisterName: String, cls: Class<*>) {
        serviceMap[serviceRegisterName] = cls
    }

    fun loadView(context: Context, viewRegisterName: String): View? {
        val output = viewMap[viewRegisterName]
        output ?: return null
        return output.getDeclaredConstructor(Context::class.java).newInstance(context)
    }

    fun loadViewClass(context: Context, viewRegisterName: String): Class<out View>? {
        return viewMap[viewRegisterName]
    }

    fun addView(viewRegisterName: String, viewCls: Class<out View>) {
        viewMap[viewRegisterName] = viewCls
    }

    companion object {
        val instance: ModuleServiceManager
            get() = SingletonHolder.instance
    }

}
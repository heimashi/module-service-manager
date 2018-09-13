package com.rong360.msm.api

import android.content.Context
import android.os.Bundle

interface IModuleService {

    fun onStartCommand(type: String, inputParams: Bundle? = null, outputParams: Bundle? = null, context: Context? = null)

}

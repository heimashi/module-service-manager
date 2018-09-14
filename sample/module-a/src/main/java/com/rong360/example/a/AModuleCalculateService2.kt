package com.rong360.example.a

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.rong360.msm.annotations.ModuleService
import com.rong360.msm.api.IModuleService

@ModuleService(register = "AModuleCalculateService2")
class AModuleCalculateService2 : IModuleService {

    override fun onStartCommand(type: String, inputParams: Bundle?, outputParams: Bundle?, context: Context?) {
        when (type) {
            "showMsg" -> {
                val msg = inputParams?.getString("MSG")
                Toast.makeText(context, "Toast From A Module:$msg", Toast.LENGTH_SHORT).show()
            }
            "calculate" -> {
                val input = inputParams?.getInt("INPUT")
                input?.let {
                    outputParams?.putInt("OUTPUT", it * 60 / 100)
                }

            }
        }
    }
}
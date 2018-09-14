package com.rong360.example.a

import android.content.Context
import android.widget.Toast
import com.rong360.example.common.IAModuleCalculateService
import com.rong360.msm.annotations.ModuleService

@ModuleService(register = "AModuleCalculateService")
class AModuleCalculateService : IAModuleCalculateService {

    override fun showMsg(context: Context) {
        Toast.makeText(context, "Toast From A Module", Toast.LENGTH_SHORT).show()
    }

    override fun calculate(input: Int): Int {
        return input * 70 / 100
    }
}
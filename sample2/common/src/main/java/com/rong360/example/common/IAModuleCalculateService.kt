package com.rong360.example.common

import android.content.Context

interface IAModuleCalculateService {

    fun showMsg(context: Context)

    fun calculate(input: Int): Int

}
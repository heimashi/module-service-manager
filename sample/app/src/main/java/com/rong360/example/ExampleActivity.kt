package com.rong360.example

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rong360.example.a.AModuleActivity
import com.rong360.example.a.AModuleCalculateService
import com.rong360.example.a.AModuleCalculateService2
import com.rong360.example.a.AModuleView
import com.rong360.example.b.BModuleActivity
import com.rong360.msm.api.ModuleServiceManager
import kotlinx.android.synthetic.main.app_activity_example.*

class ExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_example)
        addTestCode()
        toAModule.setOnClickListener {
            startActivity(Intent(this, AModuleActivity::class.java))
        }
        toBModule.setOnClickListener{
            startActivity(Intent(this, BModuleActivity::class.java))
        }
    }

    private fun addTestCode() {
        ModuleServiceManager.instance.addService("AModuleCalculateService", AModuleCalculateService::class.java)
        ModuleServiceManager.instance.addService("AModuleCalculateService2", AModuleCalculateService2::class.java)
        ModuleServiceManager.instance.addView("AModuleView", AModuleView::class.java)
    }
}

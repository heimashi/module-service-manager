package com.rong360.example

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rong360.example.a.*
import com.rong360.example.b.*
import com.rong360.msm.api.ModuleServiceManager
import kotlinx.android.synthetic.main.app_activity_example.*

class ExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_example)
        //initModules()
        toAModule.setOnClickListener {
            startActivity(Intent(this, AModuleActivity::class.java))
        }
        toBModule.setOnClickListener{
            startActivity(Intent(this, BModuleActivity::class.java))
        }
    }

    private fun initModules() {
        //ModuleServiceManager.instance.registerModules(AModuleIndex(), BModuleIndex())
    }
}

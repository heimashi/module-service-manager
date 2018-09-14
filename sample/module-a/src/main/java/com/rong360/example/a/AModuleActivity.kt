package com.rong360.example.a

import android.app.Activity
import android.os.Bundle
import com.rong360.msm.api.IModuleService
import com.rong360.msm.api.ModuleServiceManager
import kotlinx.android.synthetic.main.module_a_activity.*

class AModuleActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_a_activity)
        val bView = ModuleServiceManager.instance.loadView(this, "BModuleView")
        viewContainer.addView(bView)
        showBMsg.setOnClickListener {
            bView?.let {
                if(it is IModuleService){
                    it.onStartCommand("ShowMsg", Bundle().apply { putString("INPUT", "TEST") }, context = this@AModuleActivity)
                }
            }
        }
    }
}

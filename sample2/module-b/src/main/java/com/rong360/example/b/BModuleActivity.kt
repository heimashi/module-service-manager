package com.rong360.example.b

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import com.rong360.example.common.IAModuleCalculateService
import com.rong360.msm.api.IModuleService
import com.rong360.msm.api.ModuleServiceManager
import kotlinx.android.synthetic.main.module_b_activity.*

class BModuleActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_b_activity)

        val aView = ModuleServiceManager.instance.loadView(this, "AModuleView")
        aView?.let {
            viewContainer.addView(it)
        }

        ModuleServiceManager.instance.loadFragment("AModuleFragment")?.let {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragmentContainer, it)
            transaction.commit()
        }

        val service = ModuleServiceManager.instance.loadService("AModuleCalculateService") as IAModuleCalculateService?
        val service2 = ModuleServiceManager.instance.loadService("AModuleCalculateService2") as IModuleService?
        var flag = true
        callAMsg.setOnClickListener {
            if (flag) {
                service?.showMsg(this@BModuleActivity)
            } else {
                service2?.onStartCommand("showMsg", Bundle().apply { putString("MSG", "SERVICE2") }, context = this)
            }
        }
        callACal.setOnClickListener {
            val res: Int?
            if (flag) {
                res = service?.calculate(100)
            } else {
                val output = Bundle()
                service2?.onStartCommand("calculate", Bundle().apply { putInt("INPUT", 100) }, output)
                res = output.getInt("OUTPUT")
            }
            Toast.makeText(this@BModuleActivity, "Result:$res", Toast.LENGTH_SHORT).show()
        }
        changeService.setOnClickListener {
            flag = !flag
        }
    }
}

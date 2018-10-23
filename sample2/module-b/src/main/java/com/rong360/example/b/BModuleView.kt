package com.rong360.example.b

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Toast
import com.rong360.msm.annotations.ModuleView
import com.rong360.msm.api.IModuleService
import kotlinx.android.synthetic.main.module_b_view.view.*

@ModuleView(register = "BModuleView", desc = "Module B View")
class BModuleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context,
        attrs), IModuleService {

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        inflate(context, R.layout.module_b_view, this)
        btnB.setOnClickListener {
            Toast.makeText(context, "Toast From B View", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStartCommand(type: String, inputParams: Bundle?, outputParams: Bundle?, context: Context?) {
        when (type) {
            "ShowMsg" -> {
                inputParams?.let {
                    val msg = it.getString("INPUT")
                    Toast.makeText(context, "B View:$msg", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}

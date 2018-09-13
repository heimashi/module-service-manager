package com.rong360.example.a

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Toast
import com.rong360.msm.api.ModuleView
import kotlinx.android.synthetic.main.module_a_view.view.*

@ModuleView(register = "AModuleView")
class AModuleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        inflate(context, R.layout.module_a_view, this)
        btnA.setOnClickListener {
            Toast.makeText(context, "Toast From A View", Toast.LENGTH_SHORT).show()
        }
    }


}

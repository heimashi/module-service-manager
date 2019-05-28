package com.rong360.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v4.view.LayoutInflaterFactory
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.rong360.example.a.*
import com.rong360.example.b.*
import com.rong360.msm.api.ModuleServiceManager
import kotlinx.android.synthetic.main.app_activity_example.*


class ExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this),  object : LayoutInflaterFactory
        {
            override fun onCreateView(parent: View?, name: String?, context: Context?, attrs: AttributeSet?): View {
                Log.e("TTT", "name = " + name);
                val n = attrs!!.getAttributeCount()
                for (i in 0 until n)
                {
                    Log.e("TTT", attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
                }
                return delegate.createView(parent, name, context!!, attrs)
            }
        })
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_example)
        initModules()
        toAModule.setOnClickListener {
            startActivity(Intent(this, AModuleActivity::class.java))
        }
        toBModule.setOnClickListener{
            startActivity(Intent(this, BModuleActivity::class.java))
        }
    }

    private fun initModules() {
        ModuleServiceManager.instance.registerModules(AModuleIndex(), BModuleIndex())
    }


}

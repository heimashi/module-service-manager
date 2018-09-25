package com.rong360.example

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.app_activity_example.*

class ExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_example)
        toAModule.setOnClickListener {
            //Test code. Using router for activity is better.
            startActivity(Intent().setClassName(this@ExampleActivity, "com.rong360.example.a.AModuleActivity"))
        }
        toBModule.setOnClickListener{
            //Test code. Using router for activity is better.
            startActivity(Intent().setClassName(this@ExampleActivity, "com.rong360.example.b.BModuleActivity"))
        }
    }

}

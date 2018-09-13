package com.rong360.example.a

import android.app.Activity
import android.os.Bundle

class AModuleActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(AModuleView(this))
    }
}

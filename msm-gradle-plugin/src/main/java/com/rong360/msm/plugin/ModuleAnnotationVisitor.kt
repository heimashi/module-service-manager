package com.rong360.msm.plugin

import java.util.HashMap

import org.objectweb.asm.AnnotationVisitor

class ModuleAnnotationVisitor internal constructor(api: Int, av: AnnotationVisitor, private val nameString: String,
                                                   private val valueMap: HashMap<String, String>) : AnnotationVisitor(api, av) {

    override fun visit(name: String, value: Any) {
        super.visit(name, value)
        if ("register" == name) {
            valueMap[value as String] = nameString
        } else if ("desc" == name) {
            //todo show desc
        }
    }
}

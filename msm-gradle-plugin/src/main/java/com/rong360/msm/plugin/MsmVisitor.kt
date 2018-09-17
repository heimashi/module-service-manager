package com.rong360.msm.plugin

import java.util.HashMap

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

import com.rong360.msm.annotations.ModuleService
import com.rong360.msm.annotations.ModuleView

class MsmVisitor(api: Int, cv: ClassVisitor) : ClassVisitor(api, cv) {

    var serviceMap = HashMap<String, String>()
    var viewMap = HashMap<String, String>()
    private var className: String? = null

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces:
    Array<String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor {
        val annotationVisitor = super.visitAnnotation(desc, visible)
        if (Type.getDescriptor(ModuleService::class.java) == desc) {
            return ModuleAnnotationVisitor(Opcodes.ASM5, annotationVisitor, className!!, serviceMap)
        }
        return if (Type.getDescriptor(ModuleView::class.java) == desc) {
            ModuleAnnotationVisitor(Opcodes.ASM5, annotationVisitor, className!!, viewMap)
        } else annotationVisitor
    }

}

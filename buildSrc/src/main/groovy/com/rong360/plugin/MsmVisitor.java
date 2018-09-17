package com.rong360.plugin;

import java.util.HashMap;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;

import com.rong360.msm.annotations.ModuleService;
import com.rong360.msm.annotations.ModuleView;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MsmVisitor extends ClassVisitor {

    public HashMap<String, String> serviceMap = new HashMap<>();
    public HashMap<String, String> viewMap = new HashMap<>();
    private String className;

    public MsmVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        className = name;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, visible);
        if (Type.getDescriptor(ModuleService.class).equals(desc)) {
            return new ModuleAnnotationVisitor(Opcodes.ASM5, annotationVisitor, className, serviceMap);
        }
        if (Type.getDescriptor(ModuleView.class).equals(desc)) {
            return new ModuleAnnotationVisitor(Opcodes.ASM5, annotationVisitor, className, viewMap);
        }
        return annotationVisitor;
    }

}

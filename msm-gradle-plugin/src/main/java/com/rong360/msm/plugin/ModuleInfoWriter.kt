package com.rong360.msm.plugin

import java.util.HashMap

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

class ModuleInfoWriter : Opcodes {

    fun dump(serviceMap: HashMap<String, String>, viewMap: HashMap<String, String>, fragmentMap: HashMap<String,
            String>): ByteArray {

        val cw = ClassWriter(0)
        var fv: FieldVisitor
        var mv: MethodVisitor
        var av0: AnnotationVisitor

        cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, "com/rong360/msm/api/DefaultModuleIndex", null, "java/lang/Object",
                arrayOf("com/rong360/msm/api/IModule"))

        cw.visitSource("DefaultModuleIndex.java", null)

        run {
            fv = cw.visitField(Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "serviceMap", "Ljava/util/HashMap;",
                    "Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Class<*>;>;", null)
            fv.visitEnd()
        }
        run {
            fv = cw.visitField(Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "viewMap", "Ljava/util/HashMap;",
                    "Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Class<+Landroid/view/View;>;>;", null)
            fv.visitEnd()
        }
        run {
            fv = cw.visitField(Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "fragmentMap", "Ljava/util/HashMap;",
                    "Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Class<+Landroid/support/v4/app/Fragment;>;>;",
                    null)
            fv.visitEnd()
        }
        run {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null)
            mv.visitCode()
            val l0 = Label()
            mv.visitLabel(l0)
            mv.visitLineNumber(9, l0)
            mv.visitVarInsn(Opcodes.ALOAD, 0)
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
            mv.visitInsn(Opcodes.RETURN)
            val l1 = Label()
            mv.visitLabel(l1)
            mv.visitLocalVariable("this", "Lcom/rong360/msm/api/DefaultModuleIndex;", null, l0, l1, 0)
            mv.visitMaxs(1, 1)
            mv.visitEnd()
        }
        run {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "getModuleView", "()Ljava/util/HashMap;",
                    "()Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Class<+Landroid/view/View;>;>;", null)
            run {
                av0 = mv.visitAnnotation("Lorg/jetbrains/annotations/NotNull;", false)
                av0.visitEnd()
            }
            mv.visitCode()
            val l0 = Label()
            mv.visitLabel(l0)
            mv.visitLineNumber(16, l0)
            mv.visitFieldInsn(Opcodes.GETSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "viewMap", "Ljava/util/HashMap;")
            mv.visitInsn(Opcodes.DUP)
            val l1 = Label()
            mv.visitJumpInsn(Opcodes.IFNONNULL, l1)
            mv.visitInsn(Opcodes.ICONST_0)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/rong360/msm/api/DefaultModuleIndex", "$$\$reportNull$$$0", "(I)V",
                    false)
            mv.visitLabel(l1)
            mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, arrayOf<Any>("java/util/HashMap"))
            mv.visitInsn(Opcodes.ARETURN)
            val l2 = Label()
            mv.visitLabel(l2)
            mv.visitLocalVariable("this", "Lcom/rong360/msm/api/DefaultModuleIndex;", null, l0, l2, 0)
            mv.visitMaxs(2, 1)
            mv.visitEnd()
        }
        run {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "getModuleFragment", "()Ljava/util/HashMap;",
                    "()Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Class<+Landroid/support/v4/app/Fragment;>;>;", null)
            run {
                av0 = mv.visitAnnotation("Lorg/jetbrains/annotations/NotNull;", false)
                av0.visitEnd()
            }
            mv.visitCode()
            val l0 = Label()
            mv.visitLabel(l0)
            mv.visitLineNumber(16, l0)
            mv.visitFieldInsn(Opcodes.GETSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "fragmentMap", "Ljava/util/HashMap;")
            mv.visitInsn(Opcodes.DUP)
            val l1 = Label()
            mv.visitJumpInsn(Opcodes.IFNONNULL, l1)
            mv.visitInsn(Opcodes.ICONST_0)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/rong360/msm/api/DefaultModuleIndex", "$$\$reportNull$$$0", "(I)V",
                    false)
            mv.visitLabel(l1)
            mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, arrayOf<Any>("java/util/HashMap"))
            mv.visitInsn(Opcodes.ARETURN)
            val l2 = Label()
            mv.visitLabel(l2)
            mv.visitLocalVariable("this", "Lcom/rong360/msm/api/DefaultModuleIndex;", null, l0, l2, 0)
            mv.visitMaxs(2, 1)
            mv.visitEnd()
        }
        run {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "getModuleService", "()Ljava/util/HashMap;",
                    "()Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Class<*>;>;", null)
            run {
                av0 = mv.visitAnnotation("Lorg/jetbrains/annotations/NotNull;", false)
                av0.visitEnd()
            }
            mv.visitCode()
            val l0 = Label()
            mv.visitLabel(l0)
            mv.visitLineNumber(22, l0)
            mv.visitFieldInsn(Opcodes.GETSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "serviceMap", "Ljava/util/HashMap;")
            mv.visitInsn(Opcodes.DUP)
            val l1 = Label()
            mv.visitJumpInsn(Opcodes.IFNONNULL, l1)
            mv.visitInsn(Opcodes.ICONST_1)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/rong360/msm/api/DefaultModuleIndex", "$$\$reportNull$$$0", "(I)V",
                    false)
            mv.visitLabel(l1)
            mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, arrayOf<Any>("java/util/HashMap"))
            mv.visitInsn(Opcodes.ARETURN)
            val l2 = Label()
            mv.visitLabel(l2)
            mv.visitLocalVariable("this", "Lcom/rong360/msm/api/DefaultModuleIndex;", null, l0, l2, 0)
            mv.visitMaxs(2, 1)
            mv.visitEnd()
        }
        run {
            mv = cw.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null)
            mv.visitCode()
            val l0 = Label()
            mv.visitLabel(l0)
            mv.visitLineNumber(10, l0)
            mv.visitTypeInsn(Opcodes.NEW, "java/util/HashMap")
            mv.visitInsn(Opcodes.DUP)
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false)
            mv.visitFieldInsn(Opcodes.PUTSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "serviceMap", "Ljava/util/HashMap;")
            val l1 = Label()
            mv.visitLabel(l1)
            mv.visitLineNumber(11, l1)
            mv.visitTypeInsn(Opcodes.NEW, "java/util/HashMap")
            mv.visitInsn(Opcodes.DUP)
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false)
            mv.visitFieldInsn(Opcodes.PUTSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "viewMap", "Ljava/util/HashMap;")
            val l2 = Label()
            mv.visitLabel(l2)
            mv.visitLineNumber(11, l2)
            mv.visitTypeInsn(Opcodes.NEW, "java/util/HashMap")
            mv.visitInsn(Opcodes.DUP)
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false)
            mv.visitFieldInsn(Opcodes.PUTSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "fragmentMap",
                    "Ljava/util/HashMap;")
            var i = 26
            for (key in serviceMap.keys) {
                val value = serviceMap[key]
                val l2 = Label()
                mv.visitLabel(l2)
                mv.visitLineNumber(i++, l2)
                mv.visitFieldInsn(Opcodes.GETSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "serviceMap",
                        "Ljava/util/HashMap;")
                mv.visitLdcInsn(key)
                mv.visitLdcInsn(Type.getType("L$value;"))
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/HashMap", "put",
                        "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false)
                mv.visitInsn(Opcodes.POP)
            }
            for (key in viewMap.keys) {
                val value = viewMap[key]
                val l3 = Label()
                mv.visitLabel(l3)
                mv.visitLineNumber(i++, l3)
                mv.visitFieldInsn(Opcodes.GETSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "viewMap", "Ljava/util/HashMap;")
                mv.visitLdcInsn(key)
                mv.visitLdcInsn(Type.getType("L$value;"))
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/HashMap", "put",
                        "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false)
                mv.visitInsn(Opcodes.POP)
            }
            for (key in fragmentMap.keys) {
                val value = fragmentMap[key]
                val l4 = Label()
                mv.visitLabel(l4)
                mv.visitLineNumber(i++, l4)
                mv.visitFieldInsn(Opcodes.GETSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "fragmentMap",
                        "Ljava/util/HashMap;")
                mv.visitLdcInsn(key)
                mv.visitLdcInsn(Type.getType("L$value;"))
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/HashMap", "put",
                        "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false)
                mv.visitInsn(Opcodes.POP)
            }
            val l5 = Label()
            mv.visitLabel(l5)
            mv.visitLineNumber(i, l5)
            mv.visitInsn(Opcodes.RETURN)
            mv.visitMaxs(3, 0)
            mv.visitEnd()
        }
        run {
            mv = cw.visitMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC + Opcodes.ACC_SYNTHETIC, "$$\$reportNull$$$0", "(I)V", null, null)
            mv.visitCode()
            mv.visitLdcInsn("@NotNull method %s.%s must not return null")
            mv.visitInsn(Opcodes.ICONST_2)
            mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object")
            mv.visitInsn(Opcodes.DUP)
            mv.visitInsn(Opcodes.ICONST_0)
            mv.visitLdcInsn("com/rong360/msm/api/DefaultModuleIndex")
            mv.visitInsn(Opcodes.AASTORE)
            mv.visitVarInsn(Opcodes.ILOAD, 0)
            val l0 = Label()

            val l1 = Label()
            mv.visitTableSwitchInsn(0, 1, l0, *arrayOf(l0, l1))
            mv.visitLabel(l0)
            mv.visitFrame(Opcodes.F_FULL, 1, arrayOf<Any>(Opcodes.INTEGER), 2,
                    arrayOf<Any>("java/lang/String", "[Ljava/lang/Object;"))
            mv.visitInsn(Opcodes.DUP)
            mv.visitInsn(Opcodes.ICONST_1)
            mv.visitLdcInsn("getModuleView")
            mv.visitInsn(Opcodes.AASTORE)

            val l21 = Label()
            mv.visitJumpInsn(Opcodes.GOTO, l21)
            mv.visitLabel(l1)
            mv.visitFrame(Opcodes.F_FULL, 1, arrayOf<Any>(Opcodes.INTEGER), 2,
                    arrayOf<Any>("java/lang/String", "[Ljava/lang/Object;"))
            mv.visitInsn(Opcodes.DUP)
            mv.visitInsn(Opcodes.ICONST_1)
            mv.visitLdcInsn("getModuleFragment")
            mv.visitInsn(Opcodes.AASTORE)

            val l2 = Label()
            mv.visitJumpInsn(Opcodes.GOTO, l2)
            mv.visitLabel(l21)
            mv.visitFrame(Opcodes.F_FULL, 1, arrayOf<Any>(Opcodes.INTEGER), 2,
                    arrayOf<Any>("java/lang/String", "[Ljava/lang/Object;"))
            mv.visitInsn(Opcodes.DUP)
            mv.visitInsn(Opcodes.ICONST_1)
            mv.visitLdcInsn("getModuleService")
            mv.visitInsn(Opcodes.AASTORE)

            mv.visitJumpInsn(Opcodes.GOTO, l2)
            mv.visitLabel(l2)
            mv.visitFrame(Opcodes.F_FULL, 1, arrayOf<Any>(Opcodes.INTEGER), 2,
                    arrayOf<Any>("java/lang/String", "[Ljava/lang/Object;"))
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "format",
                    "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", false)
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalStateException")
            mv.visitInsn(Opcodes.DUP_X1)
            mv.visitInsn(Opcodes.SWAP)
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/IllegalStateException", "<init>", "(Ljava/lang/String;)V",
                    false)
            mv.visitInsn(Opcodes.ATHROW)
            mv.visitMaxs(5, 1)
            mv.visitEnd()
        }
        cw.visitEnd()

        return cw.toByteArray()
    }

}

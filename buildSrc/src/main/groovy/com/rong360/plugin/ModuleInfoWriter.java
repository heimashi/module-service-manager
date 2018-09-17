package com.rong360.plugin;

import java.util.HashMap;

import org.objectweb.asm.*;

public class ModuleInfoWriter implements Opcodes {

    public static byte[] dump(HashMap<String, String> serviceMap, HashMap<String, String> viewMap) {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "com/rong360/msm/api/DefaultModuleIndex", null, "java/lang/Object",
                new String[] {"com/rong360/msm/api/IModule"});

        cw.visitSource("DefaultModuleIndex.java", null);

        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "serviceMap", "Ljava/util/HashMap;",
                    "Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Class<*>;>;", null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "viewMap", "Ljava/util/HashMap;",
                    "Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Class<+Landroid/view/View;>;>;", null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(9, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lcom/rong360/msm/api/DefaultModuleIndex;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "getModuleView", "()Ljava/util/HashMap;",
                    "()Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Class<+Landroid/view/View;>;>;", null);
            {
                av0 = mv.visitAnnotation("Lorg/jetbrains/annotations/NotNull;", false);
                av0.visitEnd();
            }
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(16, l0);
            mv.visitFieldInsn(GETSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "viewMap", "Ljava/util/HashMap;");
            mv.visitInsn(DUP);
            Label l1 = new Label();
            mv.visitJumpInsn(IFNONNULL, l1);
            mv.visitInsn(ICONST_0);
            mv.visitMethodInsn(INVOKESTATIC, "com/rong360/msm/api/DefaultModuleIndex", "$$$reportNull$$$0", "(I)V",
                    false);
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/util/HashMap"});
            mv.visitInsn(ARETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lcom/rong360/msm/api/DefaultModuleIndex;", null, l0, l2, 0);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "getModuleService", "()Ljava/util/HashMap;",
                    "()Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Class<*>;>;", null);
            {
                av0 = mv.visitAnnotation("Lorg/jetbrains/annotations/NotNull;", false);
                av0.visitEnd();
            }
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(22, l0);
            mv.visitFieldInsn(GETSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "serviceMap", "Ljava/util/HashMap;");
            mv.visitInsn(DUP);
            Label l1 = new Label();
            mv.visitJumpInsn(IFNONNULL, l1);
            mv.visitInsn(ICONST_1);
            mv.visitMethodInsn(INVOKESTATIC, "com/rong360/msm/api/DefaultModuleIndex", "$$$reportNull$$$0", "(I)V",
                    false);
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/util/HashMap"});
            mv.visitInsn(ARETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lcom/rong360/msm/api/DefaultModuleIndex;", null, l0, l2, 0);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(10, l0);
            mv.visitTypeInsn(NEW, "java/util/HashMap");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false);
            mv.visitFieldInsn(PUTSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "serviceMap", "Ljava/util/HashMap;");
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(11, l1);
            mv.visitTypeInsn(NEW, "java/util/HashMap");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false);
            mv.visitFieldInsn(PUTSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "viewMap", "Ljava/util/HashMap;");
            int i = 26;
            for (String key : serviceMap.keySet()) {
                String value = serviceMap.get(key);
                Label l2 = new Label();
                mv.visitLabel(l2);
                mv.visitLineNumber(i++, l2);
                mv.visitFieldInsn(GETSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "serviceMap",
                        "Ljava/util/HashMap;");
                mv.visitLdcInsn(key);
                mv.visitLdcInsn(Type.getType("L" + value + ";"));
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashMap", "put",
                        "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false);
                mv.visitInsn(POP);
            }
            for (String key : viewMap.keySet()) {
                String value = viewMap.get(key);
                Label l3 = new Label();
                mv.visitLabel(l3);
                mv.visitLineNumber(i++, l3);
                mv.visitFieldInsn(GETSTATIC, "com/rong360/msm/api/DefaultModuleIndex", "viewMap", "Ljava/util/HashMap;");
                mv.visitLdcInsn(key);
                mv.visitLdcInsn(Type.getType("L" + value + ";"));
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashMap", "put",
                        "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false);
                mv.visitInsn(POP);
            }
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(i, l4);
            mv.visitInsn(RETURN);
            mv.visitMaxs(3, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC, "$$$reportNull$$$0", "(I)V", null, null);
            mv.visitCode();
            mv.visitLdcInsn("@NotNull method %s.%s must not return null");
            mv.visitInsn(ICONST_2);
            mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitLdcInsn("com/rong360/msm/api/DefaultModuleIndex");
            mv.visitInsn(AASTORE);
            mv.visitVarInsn(ILOAD, 0);
            Label l0 = new Label();
            Label l1 = new Label();
            mv.visitTableSwitchInsn(0, 1, l0, new Label[] {l0, l1});
            mv.visitLabel(l0);
            mv.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 2,
                    new Object[] {"java/lang/String", "[Ljava/lang/Object;"});
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitLdcInsn("getModuleView");
            mv.visitInsn(AASTORE);
            Label l2 = new Label();
            mv.visitJumpInsn(GOTO, l2);
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 2,
                    new Object[] {"java/lang/String", "[Ljava/lang/Object;"});
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitLdcInsn("getModuleService");
            mv.visitInsn(AASTORE);
            mv.visitJumpInsn(GOTO, l2);
            mv.visitLabel(l2);
            mv.visitFrame(Opcodes.F_FULL, 1, new Object[] {Opcodes.INTEGER}, 2,
                    new Object[] {"java/lang/String", "[Ljava/lang/Object;"});
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "format",
                    "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", false);
            mv.visitTypeInsn(NEW, "java/lang/IllegalStateException");
            mv.visitInsn(DUP_X1);
            mv.visitInsn(SWAP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalStateException", "<init>", "(Ljava/lang/String;)V",
                    false);
            mv.visitInsn(ATHROW);
            mv.visitMaxs(5, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

}

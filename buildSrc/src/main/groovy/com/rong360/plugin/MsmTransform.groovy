package com.rong360.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import org.apache.commons.io.IOUtils
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import com.android.build.api.transform.Format
import com.android.build.gradle.internal.pipeline.TransformManager
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.gradle.api.Project
import org.objectweb.asm.Opcodes

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class MsmTransform extends Transform {

    private Project project
    public HashMap<String, String> serviceMap = new HashMap<>()
    public HashMap<String, String> viewMap = new HashMap<>()

    MsmTransform(Project iProject) {
        project = iProject
    }

    @Override
    String getName() {
        return "MsmTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        transformInvocation.inputs.each { input ->
            input.directoryInputs.each { directoryInput ->
                if (directoryInput.file.isDirectory()) {
                    directoryInput.file.eachFileRecurse { File file ->
                        def name = file.name
                        if (name.endsWith(".class")) {
                            ClassReader classReader = new ClassReader(file.bytes)
                            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            MsmVisitor msmVisitor = new MsmVisitor(Opcodes.ASM5, classWriter)
                            classReader.accept(msmVisitor, ClassReader.EXPAND_FRAMES)
                            serviceMap.putAll(msmVisitor.serviceMap)
                            viewMap.putAll(msmVisitor.viewMap)
                        }
                    }
                }
                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput
                        .contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            input.jarInputs.each { jarInput ->
                String filePath = jarInput.file.getAbsolutePath()
                if (filePath.endsWith(".jar")
                        && !filePath.contains("com.android.support")
                        && !filePath.contains("/com/android/support")) {
                    JarFile jarFile = new JarFile(jarInput.file)
                    Enumeration enumeration = jarFile.entries()
                    while (enumeration.hasMoreElements()) {
                        JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                        String entryName = jarEntry.getName()
                        if (entryName.endsWith(".class")) {
                            InputStream inputStream = jarFile.getInputStream(jarEntry)
                            ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream))
                            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            MsmVisitor msmVisitor = new MsmVisitor(Opcodes.ASM5, classWriter)
                            classReader.accept(msmVisitor, ClassReader.EXPAND_FRAMES)
                            serviceMap.putAll(msmVisitor.serviceMap)
                            viewMap.putAll(msmVisitor.viewMap)
                            inputStream.close()
                        }
                    }
                    jarFile.close()
                }

                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes,
                        jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)

            }

        }
        println "========= service ========="
        serviceMap.each {
            println "service:" + it.key + " " + it.value
        }
        println "=========   view  ========="
        viewMap.each {
            println "view:" + it.key + " " + it.value
        }
        ModuleInfoWriter moduleInfoWriter = new ModuleInfoWriter()
        File meta_file = transformInvocation.outputProvider.getContentLocation("Msm", getOutputTypes(), getScopes()
                , Format.JAR)
        if (!meta_file.getParentFile().exists()) {
            meta_file.getParentFile().mkdirs()
        }
        if (meta_file.exists()) {
            meta_file.delete()
        }

        FileOutputStream fos = new FileOutputStream(meta_file)
        JarOutputStream jarOutputStream = new JarOutputStream(fos)
        ZipEntry zipEntry = new ZipEntry("com/rong360/msm/api/DefaultModuleIndex.class")
        jarOutputStream.putNextEntry(zipEntry)
        jarOutputStream.write(moduleInfoWriter.dump(serviceMap, viewMap))
        jarOutputStream.closeEntry()
        jarOutputStream.close()
        fos.close()


    }
}
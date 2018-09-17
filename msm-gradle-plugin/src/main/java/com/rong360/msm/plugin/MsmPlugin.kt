package com.rong360.msm.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project


class MsmPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.all{
            when(it){
                is AppPlugin -> {
                    val android = project.extensions.getByType(AppExtension::class.java)
                    android.registerTransform(MsmServiceTransform())
                }
            }
        }
    }
}

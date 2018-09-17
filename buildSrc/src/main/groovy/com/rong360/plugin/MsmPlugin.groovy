package com.rong360.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MsmPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.android.registerTransform(new MsmTransform(project))
    }

}
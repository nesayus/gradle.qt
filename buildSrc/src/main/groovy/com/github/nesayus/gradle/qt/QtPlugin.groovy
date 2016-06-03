package com.github.nesayus.gradle.qt

import org.apache.commons.lang.StringUtils
import org.gradle.model.Defaults
import org.gradle.model.Mutate
import org.gradle.model.ModelMap
import org.gradle.model.Path
import org.gradle.model.RuleSource
import org.gradle.platform.base.BinarySpec
import org.gradle.language.cpp.CppSourceSet
import org.gradle.language.cpp.tasks.CppCompile

class QtPlugin extends RuleSource {

    @Defaults
    void createQtMocTask(ModelMap<BinarySpec> binaries, final @Path("buildDir") File buildDir) {
        binaries.beforeEach { binary ->
            binary.inputs.withType(CppSourceSet.class) { sourceSet ->
                def taskName = "${binary.getNamingScheme().getTaskName('qtMoc')}${StringUtils.capitalize(sourceSet.parentName)}${StringUtils.capitalize(sourceSet.name)}"
                binary.tasks.create(taskName, QtMocTask) { task ->
                    task.source = sourceSet.source
                    task.destinationDir = new File(buildDir, "generated/${binary.name}/moc")
                    task.description = "Creates qt moc-files for ${binary.displayName}"
                }

                sourceSet.source.srcDir(new File(buildDir, "generated/${binary.name}/moc"))
                binary.tasks.withType(CppCompile.class) { compileTask ->
                    compileTask.dependsOn(taskName)
                }
            }
        }
    }
}


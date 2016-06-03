package com.github.nesayus.gradle.qt

import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

class QtMocTask extends SourceTask {
    @OutputDirectory
    File destinationDir
        
    @TaskAction
    void generateMocFile() {
         source.filter { x -> x.isFile() && x.text.contains('Q_OBJECT') }.each {
             def mocFileName = "${destinationDir}/moc_${it.name}.cpp"
             def process = "moc -o ${mocFileName} ${it.path}".execute()
             process.waitFor()
             if (process.exitValue()) {
                 println process.err.text
             } 
         }
    }
}


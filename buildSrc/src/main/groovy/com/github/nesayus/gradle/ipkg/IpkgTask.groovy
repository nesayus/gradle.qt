package com.github.nesayus.gradle.ipkg 
 
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import org.gradle.api.internal.file.copy.CopyAction

class ipkgTask extends AbstractArchiveTask {
    CopyAction createCopyAction() {
    }
}

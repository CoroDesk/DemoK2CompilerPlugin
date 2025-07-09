plugins {
    kotlin("multiplatform") version("2.1.21") apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
    id("com.android.library") version "8.10.0" apply false
}

// Register the publication task that runs all submodules in order
tasks.register("publishAllToLocalMaven") {
    group = "publishing"
    description = "Publishes all submodules to Maven Local in dependency order"
    
    // Define the publication order based on dependencies
    val publicationOrder = listOf( ":names", ":compiler-plugin", ":gradle-plugin")
    
    doFirst {
        println("Publishing all dependencies in order: ${publicationOrder.joinToString(" -> ")}")
    }
    
    // Create dependencies between subproject publication tasks
    publicationOrder.forEachIndexed { index, projectPath ->
        val currentProject = project(projectPath)
        val publishTask = currentProject.tasks.findByName("publishToMavenLocal")
        
        if (publishTask != null) {
            dependsOn(publishTask)
            
            // Add dependency on previous module if not the first one
            if (index > 0) {
                val previousProjectPath = publicationOrder[index - 1]
                val previousProject = project(previousProjectPath)
                val previousPublishTask = previousProject.tasks.findByName("publishToMavenLocal")
                
                if (previousPublishTask != null) {
                    publishTask.mustRunAfter(previousPublishTask)
                }
            }
        }
    }
}
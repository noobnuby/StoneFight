import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
}

group = property("group")!!
version = property("version")!!
val copy_dir = "${property("copy_dir")}"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("io.github.monun:kommand-api:3.1.8")
    compileOnly("io.papermc.paper:paper-api:${property("paper_version")}-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(project.properties)
        }
    }

    create<Jar>("paperJar") {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")

        from(sourceSets["main"].output)

        if(copy_dir != "") {
            doLast {
                copy {
                    val dir = File(copy_dir)
                    from(archiveFile)
                    into(if (File(dir, archiveFileName.get()).exists()) dir else dir)
                }
            }
        }
    }
}

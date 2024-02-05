import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = property("group")!!
version = property("version")!!
val copy_dir = "${property("copy_dir")}"

repositories {
    mavenCentral()
    mavenLocal()
    flatDir {
        dirs("libs")
    }
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("xyz.icetang.lib:kommand-api:3.1.11")
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
                    from(archiveFile)
                    into(if (File(copy_dir, archiveFileName.get()).exists()) copy_dir else copy_dir)
                }
            }
        }
    }
}

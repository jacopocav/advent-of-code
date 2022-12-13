import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
}

allprojects {
    group = "ceejay.advent"
    version = "1.0-SNAPSHOT"

    apply {
        plugin(KotlinPlatformJvmPlugin::class)
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(kotlin("test"))
        testImplementation("org.assertj:assertj-core:3.23.1")
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}
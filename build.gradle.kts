/*
 * Copyright (c) 2021 Wolf-Martell Montwé. All rights reserved.
 */

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven {
            url = uri("https://maven.pkg.github.com/bitfunk/gradle-git-version")
            credentials {
                username = project.findProperty("githubPackageDownload.user") as? String
                    ?: System.getenv("GITHUB_PACKAGE_DOWNLOAD_USER")
                password = project.findProperty("githubPackageDownload.key") as? String
                    ?: System.getenv("GITHUB_PACKAGE_DOWNLOAD_KEY")
            }
        }
    }

    dependencies {
        classpath(libs.gradleKotlinPlugin)
        classpath(libs.gradleAndroidPlugin)
        classpath(libs.gradleSqlDelightPlugin)
        classpath(libs.gradleMokoResourcesGeneratorPlugin)
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.gradleBlueprintDependency)

    alias(libs.plugins.gradleBlueprintQualitySpotless)
    alias(libs.plugins.gradleBlueprintQualityDetekt)
    alias(libs.plugins.gradleBlueprintVersion)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    configurations.all {
        resolutionStrategy.eachDependency {
            if ("org.jacoco" == requested.group) {
                useVersion(libs.versions.jacoco.get())
            }
        }
    }
}

tasks.maybeCreate("clean", Delete::class.java).delete("build")

tasks.named<Wrapper>("wrapper") {
    gradleVersion = libs.versions.gradle.get()
    distributionType = Wrapper.DistributionType.ALL
}

tasks.register("wrapperAll") {
    dependsOn("wrapper")
    dependsOn(gradle.includedBuilds.map { it.task(":wrapper") })
}

tasks.register("dependencyUpdatesAll") {
    dependsOn("dependencyUpdates")
    dependsOn(gradle.includedBuilds.map { it.task(":dependencyUpdates") })
}

tasks.register("versionCatalogUpdateAll") {
    dependsOn("versionCatalogUpdate")
    dependsOn(gradle.includedBuilds.map { it.task(":versionCatalogUpdate") })
}

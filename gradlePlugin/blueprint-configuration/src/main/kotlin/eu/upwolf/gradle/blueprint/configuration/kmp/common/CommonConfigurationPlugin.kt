/*
 * Copyright (c) 2021 Wolf-Martell Montwé. All rights reserved.
 */

package eu.upwolf.gradle.blueprint.configuration.kmp.common

import eu.upwolf.gradle.blueprint.dependency.Dependency
import eu.upwolf.gradle.blueprint.dependency.Version
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CommonConfigurationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.multiplatform")
        target.pluginManager.apply("eu.upwolf.gradle.blueprint.configuration.android.library")

        setupMultiplatformLibrary(target)
        setupTargets(target)
    }

    private fun setupMultiplatformLibrary(project: Project) {
        project.kotlin {
            sourceSets {
                maybeCreate("commonMain").dependencies {
                    implementation(Dependency.Kotlin.StdLib.common)
                    implementation(Dependency.Kotlin.Coroutines.common)
                }

                maybeCreate("commonTest").dependencies {
                    implementation(Dependency.Kotlin.Test.common)
                    implementation(Dependency.Kotlin.Test.commonAnnotations)
                }
            }
        }

        project.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    private fun setupTargets(project: Project) {
        setupAndroidTarget(project)
        setupIosTarget(project)
    }

    private fun setupAndroidTarget(project: Project) {
        project.pluginManager.apply("com.android.library")

        project.kotlin {
            android {
                publishLibraryVariants("release")
            }

            sourceSets {
                maybeCreate("androidMain").dependencies {
                    implementation(Dependency.Kotlin.StdLib.android)
                    implementation(Dependency.Kotlin.Coroutines.android)
                }
                maybeCreate("androidTest").dependencies {
                    implementation(Dependency.Kotlin.Test.jvmJunit)
                    implementation(Dependency.Kotlin.Coroutines.test)
                }
            }
        }
    }

    private fun setupIosTarget(project: Project) {
        project.kotlin {
            ios { }

            sourceSets {
                maybeCreate("iosMain").dependencies {
                    implementation(Dependency.Kotlin.Coroutines.common)
                }
            }
        }
    }

    private fun Project.kotlin(action: Action<KotlinMultiplatformExtension>) {
        extensions.configure(KotlinMultiplatformExtension::class.java, action)
    }
}
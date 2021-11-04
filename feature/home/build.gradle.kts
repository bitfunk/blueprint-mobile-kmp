/*
 * Copyright (c) 2021 Wolf-Martell Montwé. All rights reserved.
 */

plugins {
    id("eu.upwolf.gradle.blueprint.configuration.kmp.feature")
}

kotlin {
    sourceSets {
        val commonMain by getting
        val commonTest by getting
        val androidMain by getting
        val androidTest by getting
        val iosMain by getting
        val iosTest by getting
    }
}

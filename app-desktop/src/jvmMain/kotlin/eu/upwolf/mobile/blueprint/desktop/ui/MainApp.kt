/*
 * Copyright (c) 2021 Wolf-Martell Montwé. All rights reserved.
 */

package eu.upwolf.mobile.blueprint.desktop.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import eu.upwolf.mobile.blueprint.common.theme.MainTheme

@Composable
fun MainApp() {
    MainTheme {
        Text(text = "Test")
    }
}

package com.salt.saltandroidtest.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kanzankazu.kanzanwidget.compose.extension.initMod
import com.salt.core.compose.component.text.TypewriterText
import com.salt.core.compose.ui.theme.BaseTheme

@Composable
fun SplashScreen(stringList: List<String>, onFinish: () -> Unit = {}) {
    Column(
        modifier = initMod()
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentSize()
    ) {
        TypewriterText(
            stringList = stringList,
            modifier = initMod(),
            onFinish = onFinish
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    BaseTheme {
        SplashScreen(listOf("Splash Screen Preview"))
    }
}
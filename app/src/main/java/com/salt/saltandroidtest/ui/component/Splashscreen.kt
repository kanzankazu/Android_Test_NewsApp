package com.salt.saltandroidtest.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.kanzankazu.kanzanwidget.compose.extension.initMod
import com.salt.core.compose.TypewriterText

@Composable
fun SplashScreen(stringList: List<String>, onFinish: () -> Unit = {}) {
    Column(
        modifier = initMod()
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentSize()
    ) {
        Box(
            modifier = initMod()
        ) {
            TypewriterText(
                stringList = stringList,
                modifier = initMod(),
                onFinish = onFinish
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(listOf("Splash Screen Preview"), {})
}
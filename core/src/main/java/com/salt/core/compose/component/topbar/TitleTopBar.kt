package com.salt.core.compose.component.topbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanzankazu.kanzanwidget.compose.extension.initMod
import com.salt.core.compose.ui.PrimaryDark
import com.salt.core.compose.ui.nunitoFamily

@Composable
fun TitleTopBar(title: String) {
    TopAppBar(
        backgroundColor = PrimaryDark,
    ) {
        Text(
            modifier = initMod()
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            text = title,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = nunitoFamily,
            fontWeight = FontWeight.Bold
        )
    }
}
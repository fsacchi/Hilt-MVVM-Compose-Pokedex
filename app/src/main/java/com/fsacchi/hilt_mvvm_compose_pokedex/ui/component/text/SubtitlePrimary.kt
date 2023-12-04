package com.fsacchi.hilt_mvvm_compose_pokedex.ui.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.theme.subTitlePrimary

@Composable
fun SubtitlePrimary(text: String) {
    Text(
        text = text,
        style = subTitlePrimary
    )
}

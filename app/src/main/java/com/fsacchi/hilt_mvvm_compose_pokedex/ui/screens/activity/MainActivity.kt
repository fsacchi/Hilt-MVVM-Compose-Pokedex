package com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.mainscreen.MainScreen
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.theme.HiltMVVMComposePokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val splashViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { splashViewModel.isLoading.value }
        }
        setContent {
            HiltMVVMComposePokedexTheme {
                MainScreen()
            }
        }
    }
}
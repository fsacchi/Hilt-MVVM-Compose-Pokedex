package com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.fsacchi.hilt_mvvm_compose_pokedex.R
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.ResultItem
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.TypesPokemon
import com.fsacchi.hilt_mvvm_compose_pokedex.navigation.Navigation
import com.fsacchi.hilt_mvvm_compose_pokedex.navigation.Screen
import com.fsacchi.hilt_mvvm_compose_pokedex.navigation.currentRoute
import com.fsacchi.hilt_mvvm_compose_pokedex.navigation.navigationTitle
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.component.appbar.AppBarWithArrow
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.component.appbar.HomeAppBar
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.drawer.DrawerUI
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.AppConstant
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.network.DataState
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.networkconnection.ConnectionState
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.networkconnection.connectivityState
import kotlinx.coroutines.launch


@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    val genreName = remember { mutableStateOf("") }
    val typesList = remember { mutableStateOf(arrayListOf<ResultItem>()) }
    // internet connection
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    // faz chamar o endpoint de tipos ao criar a tela
    LaunchedEffect(key1 = 0) {
        mainViewModel.typeList()
    }

    if (mainViewModel.types.value is DataState.Success<TypesPokemon>) {
        typesList.value =
            (mainViewModel.types.value as DataState.Success<TypesPokemon>).data.types as ArrayList
        // All first value as all
        if (typesList.value.first().name != AppConstant.DEFAULT_TYPE_ITEM)
            typesList.value.add(0, ResultItem(AppConstant.DEFAULT_TYPE_ITEM, ""))
    }

    Scaffold(scaffoldState = scaffoldState, topBar = {
        when (currentRoute(navController)) {
            Screen.Home.route, Screen.NavigationDrawer.route -> {
                if (isAppBarVisible.value) {
                    val appTitle: String =
                        if (currentRoute(navController) == Screen.NavigationDrawer.route) genreName.value
                        else stringResource(R.string.app_title)
                    HomeAppBar(title = appTitle, openDrawer = {
                        scope.launch {
                            scaffoldState.drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }, openFilters = {
                        isAppBarVisible.value = false
                    })
                }
            }
            else -> {
                AppBarWithArrow(navigationTitle(navController)) {
                    navController.popBackStack()
                }
            }
        }
    }, drawerContent = {
        // Drawer content
        DrawerUI(navController, typesList.value as List<ResultItem>) {
            genreName.value = it
            scope.launch {
                scaffoldState.drawerState.close()
            }
        }
    }, snackbarHost = {
        if (isConnected.not()) {
            Snackbar(
                action = {}, modifier = Modifier.padding(8.dp)
            ) {
                Text(text = stringResource(R.string.there_is_no_internet))
            }
        }
    }) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Navigation(navController, Modifier.padding(it), typesList.value)
        }
    }
}
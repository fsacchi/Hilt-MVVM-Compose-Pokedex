package com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CatchingPokemon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.ResultItem
import com.fsacchi.hilt_mvvm_compose_pokedex.navigation.Screen
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.AppConstant

@Composable
fun DrawerUI(
    navController: NavController,
    types: List<ResultItem>,
    closeDrawer: (typeName: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(items = types, itemContent = { item ->
            DrawerItem(
                item = item
            ) {
                if (it.name == AppConstant.DEFAULT_TYPE_ITEM) {
                    navController.navigate(Screen.Home.route)
                } else {
                    navController.navigate(Screen.NavigationDrawer.route.plus("/${it.name}")) {
                        launchSingleTop = true
                    }
                }

                // Close drawer
                closeDrawer(it.name)
            }
        })
    }
}

@Composable
fun DrawerItem(item: ResultItem, onItemClick: (ResultItem) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .padding(start = 10.dp)
    ) {
        Icon(
            Icons.Outlined.CatchingPokemon, "", modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.name,
            fontSize = 18.sp
        )
    }
}

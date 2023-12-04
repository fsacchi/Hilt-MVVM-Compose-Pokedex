package com.fsacchi.hilt_mvvm_compose_pokedex.ui.component

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.fsacchi.hilt_mvvm_compose_pokedex.data.datasource.remote.ApiURL
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.ResultItem
import com.fsacchi.hilt_mvvm_compose_pokedex.navigation.Screen
import com.fsacchi.hilt_mvvm_compose_pokedex.navigation.currentRoute
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.theme.DefaultBackgroundColor
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.theme.PokedexColor
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.theme.PokedexLight
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.theme.cornerRadius
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.conditional
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.items
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.pagingLoadingState
import kotlinx.coroutines.flow.Flow

@Composable
fun PokemonItemList(
    navController: NavController,
    pokemons: Flow<PagingData<ResultItem>>,
    types: ArrayList<ResultItem>? = null,
    selectedName: ResultItem?,
    onclick: (genreId: ResultItem?) -> Unit
) {
    val activity = (LocalContext.current as? Activity)
    val progressBar = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val pokemonItems: LazyPagingItems<ResultItem> = pokemons.collectAsLazyPagingItems()
    val lazyGridState = rememberLazyGridState()

    BackHandler(enabled = (currentRoute(navController) == Screen.Home.route)) {
        openDialog.value = true
    }

    LaunchedEffect(pokemonItems) {
        snapshotFlow { pokemonItems.loadState.refresh }
            .collect { loadState ->
                if (loadState is LoadState.NotLoading) {
                        lazyGridState.scrollToItem(index = 0)
                }
            }
    }

    Column(modifier = Modifier.background(DefaultBackgroundColor)) {
        types?.let {
            LazyRow(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 9.dp, end = 9.dp)
                    .fillMaxWidth()
            ) {
                items(types) { item ->
                    SelectableGenreChip(
                        selected = item.name === selectedName?.name,
                        genre = item.name,
                        onclick = {
                            onclick(item)
                        }
                    )
                }
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
        LazyVerticalGrid(columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .conditional(types == null) {
                    padding(top = 8.dp)
                },
            content = {
                items(pokemonItems) { item ->
                    item?.let {
                        PokemonItemView(it, navController)
                    }
                }
            },
            state = lazyGridState)
    }
    if (openDialog.value) {
        ExitAlertDialog(navController, {
            openDialog.value = it
        }, {
            activity?.finish()
        })

    }
    pokemonItems.pagingLoadingState {
        progressBar.value = it
    }
}

@Composable
fun PokemonItemView(item: ResultItem, navController: NavController) {
    Box(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp)) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .decoderFactory(SvgDecoder.Factory())
                .data(ApiURL.IMAGE_URL.plus(item.getNameImage()) )
                .build()
        )

        Image(
            modifier = Modifier
                .size(150.dp)
                .cornerRadius(10)
                .shadow(8.dp)
                .background(color = Color.White)
                .clickable {
                    navController.navigate(Screen.PokemonDetail.route.plus("/${item.getNameItem()}"))
                },
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
        )

        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CircularIndeterminateProgressBar(isDisplayed = true, 0.6f)
            }

            else -> {}
        }

        Text(
            text = item.name.replaceFirstChar { it.uppercase() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SelectableGenreChip(
    selected: Boolean,
    genre: String,
    onclick: () -> Unit
) {

    val animateChipBackgroundColor by animateColorAsState(
        targetValue = if (selected) PokedexColor else PokedexLight,
        animationSpec = tween(
            durationMillis = 50,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .cornerRadius(16)
            .background(
                color = animateChipBackgroundColor
            )
            .height(32.dp)
            .widthIn(min = 80.dp)
            .padding(horizontal = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onclick()
            }
    ) {
        Text(
            text = genre,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            color = Color.White.copy(alpha = 0.80F)
        )
    }
}




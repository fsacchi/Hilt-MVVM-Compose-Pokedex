package com.fsacchi.hilt_mvvm_compose_pokedex.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.fsacchi.hilt_mvvm_compose_pokedex.R
import com.fsacchi.hilt_mvvm_compose_pokedex.data.datasource.remote.ApiURL
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.pokemondetail.PokemonDetail
import com.fsacchi.hilt_mvvm_compose_pokedex.data.model.pokemondetail.TypesPokemon
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.component.CircularIndeterminateProgressBar
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.component.text.SubtitlePrimary
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.component.text.SubtitleSecondary
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.theme.DefaultBackgroundColor
import com.fsacchi.hilt_mvvm_compose_pokedex.ui.theme.FontColor
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.network.DataState
import com.fsacchi.hilt_mvvm_compose_pokedex.utils.pagingLoadingState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun PokemonDetail(pokemonId: Int) {
    val pokemonDetailViewModel = hiltViewModel<PokemonDetailViewModel>()
    val progressBar = remember { mutableStateOf(false) }
    val pokemonDetail = pokemonDetailViewModel.pokemonDetail

    LaunchedEffect(true) {
        pokemonDetailViewModel.pokemonDetailApi(pokemonId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DefaultBackgroundColor
            )

    ) {
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)
        pokemonDetail.value?.let {
            if (it is DataState.Success<PokemonDetail>) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // Image Pokemon
                    item {
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .decoderFactory(SvgDecoder.Factory())
                                .data(ApiURL.IMAGE_URL.plus(it.data.getNameImage()) )
                                .build()
                        )

                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                        )

                        when (painter.state) {
                            is AsyncImagePainter.State.Loading -> {
                                CircularIndeterminateProgressBar(isDisplayed = true, 0.6f)
                            }

                            else -> {}
                        }
                    }

                    // Stats Pokemon
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp, end = 10.dp)
                        ) {
                            Text(
                                text = it.data.name.replaceFirstChar { it.uppercase() },
                                modifier = Modifier.padding(top = 10.dp),
                                color = FontColor,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.W700,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp, top = 10.dp)
                            ) {

                                Column(Modifier.weight(1f)) {
                                    SubtitlePrimary(
                                        text = "#${it.data.order}",
                                    )
                                    SubtitleSecondary(
                                        text = stringResource(R.string.number)
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    SubtitlePrimary(
                                        text = "${it.data.baseExperience}",
                                    )
                                    SubtitleSecondary(
                                        text = stringResource(R.string.base_experience)
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    SubtitlePrimary(
                                        text = it.data.getHeightFormatted(),
                                    )
                                    SubtitleSecondary(
                                        text = stringResource(R.string.height)
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    SubtitlePrimary(
                                        text = it.data.getWeightFormatted(),
                                    )
                                    SubtitleSecondary(
                                        text = stringResource(R.string.weight)
                                    )
                                }
                            }
                        }


                    }

                    // Types Pokemon
                    item {
                        Text(
                            text = stringResource(R.string.types),
                            color = FontColor,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                        )
                    }

                    items(items = it.data.types) { type ->
                        PokemonType(type = type)
                    }

                    // Sprites
                    item {
                        Text(
                            text = stringResource(R.string.sprites),
                            color = FontColor,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                        )

                        it.data.sprites.apply {
                            PokemonSprite(urlFront = frontDefault, urlBack = backDefault)
                            PokemonSprite(urlFront = frontShiny, urlBack = frontShiny)
                        }
                    }
                }


            }
        }

        pokemonDetail.pagingLoadingState {
            progressBar.value = it
        }
    }
}

@Preview(name = "PokemonDetail", showBackground = true)
@Composable
fun Preview() {
    PokemonDetail(1)
}

@Composable
fun PokemonType(type: TypesPokemon) {
    Row(modifier = Modifier.padding(top = 16.dp, start = 16.dp)) {
        CoilImage(
            modifier = Modifier
                .height(30.dp)
                .width(30.dp),
            imageModel = { ApiURL.IMAGE_URL_TYPE.plus("${type.type.name}.png") },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                contentDescription = "",
                colorFilter = null,
            )
        )

        Text(
            text = type.type.name.replaceFirstChar { firstChar -> firstChar.uppercase() },
            color = FontColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun PokemonSprite(urlFront: String, urlBack: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, top = 10.dp)
    ) {

        Column(modifier = Modifier.weight(1f)) {
            PokemonSpriteImage(urlFront)
        }
        Column(modifier = Modifier.weight(1f)) {
            PokemonSpriteImage(urlBack)
        }
    }
}

@Composable
fun PokemonSpriteImage(url: String) {
    val painterSprite = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .build()
    )

    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        painter = painterSprite,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center,
    )

    when (painterSprite.state) {
        is AsyncImagePainter.State.Loading -> {
            CircularIndeterminateProgressBar(isDisplayed = true, 0.6f)
        }

        else -> {}
    }
}


package com.android.bloomshows.presentation.MovieDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.android.bloomshows.R
import com.android.bloomshows.data.room.entity.MovieEntity
import com.android.bloomshows.ui.common_components.CoilImage
import com.android.bloomshows.ui.common_components.MovieTopAppBar
import com.android.bloomshows.ui.theme.LargePadding
import com.android.bloomshows.ui.theme.MediumPadding

@Composable
fun MovieDetailsScreen() {

}

@Composable
fun MovieDetailsScreen(movie: MovieEntity, navigateBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        MoviePosterCard(movie, navigateBack)
//        MiddleInfoSection(pokemonInfo = pokemoninfo)
//        PokemonBottomStats(pokemonInfo = pokemoninfo)
    }
}

@Composable
fun MoviePosterCard(movie: MovieEntity, navigateBack: () -> Unit) {
    Box(
        modifier = Modifier.requiredHeight(420.dp).fillMaxWidth().clip(
            RoundedCornerShape(bottomStart = LargePadding, bottomEnd = LargePadding),
        )
    ) {
        movie.title?.let {
            MovieTopAppBar(
                modifier = Modifier.zIndex(100f),
                title = it,
                containerColor = Color.Transparent,
                id = movie.id.toString(),
                handleNavBack = navigateBack
            )
        }
        CoilImage(
            modifier = Modifier.fillMaxSize(),
            data = movie.backdropPath,
            placeholder = painterResource(R.drawable.ic_bloomshows),
            contentScale = ContentScale.FillBounds,
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}
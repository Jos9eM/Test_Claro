package com.hitss.test_claro.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import coil3.compose.AsyncImage
import com.hitss.test_claro.domain.model.MovieItem
import org.jetbrains.compose.resources.painterResource
import test_claro.composeapp.generated.resources.Res
import test_claro.composeapp.generated.resources.estrella
import kotlin.math.absoluteValue

@Composable
fun CarouselCard(movieList: List<MovieItem>, onClick: (MovieItem) -> Unit) {
    val pagerState = rememberPagerState(initialPage = 0) {
        movieList.size
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 65.dp),
        modifier = Modifier
            .height(350.dp)
    ) { index ->

        val pagerOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.graphicsLayer {
                lerp(
                    start = 0.85f.dp,
                    stop = 1f.dp,
                    fraction = 1f - pagerOffset.absoluteValue.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale.value
                    scaleY = scale.value
                }
            },
            onClick = { onClick(movieList[index]) }
        ) {
            val urlImage = movieList[index].posterPath
            AsyncImage(
                model = urlImage,
                contentDescription = "Película",
                contentScale = ContentScale.Crop,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(Res.drawable.estrella),
                    contentDescription = "Logo",
                    modifier = Modifier.size(15.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(2.dp))
                val voteAverage = (movieList[index].voteAverage * 10).toInt() / 10.0
                Text(voteAverage.toString())
            }
            Text(movieList[index].title)
        }
    }
}

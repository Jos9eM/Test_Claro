package com.hitss.test_claro.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.hitss.test_claro.domain.model.MovieDTO
import org.jetbrains.compose.resources.painterResource
import test_claro.composeapp.generated.resources.Res
import test_claro.composeapp.generated.resources.estrella

@Composable
fun DetailMovie(movie: MovieDTO?) {

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = movie?.backdropPath?: "",
            contentDescription = "Película",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.padding(10.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(movie?.title ?: "", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Card(shape = RoundedCornerShape(8.dp)) {
                        Text(modifier = Modifier.padding(4.dp), text = movie?.releaseDate ?: "")
                    }
                    Spacer(modifier = Modifier.width(5.dp))

                    Card(shape = RoundedCornerShape(8.dp)) {
                        Text(modifier = Modifier.padding(4.dp), text = "${movie?.runtime} min")
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Card(shape = RoundedCornerShape(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(Res.drawable.estrella),
                                contentDescription = "Logo",
                                modifier = Modifier.size(15.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            movie?.let {
                                val voteAverage = (it.voteAverage * 10).toInt() / 10.0
                                Text(modifier = Modifier.padding(4.dp), text = voteAverage.toString())
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp)) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("Titulo original", color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(movie?.originalTitle ?: "", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp)) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("Descripción", color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(movie?.overview ?: "", color = Color.White)
                    }
                }
            }
        }
    }
}
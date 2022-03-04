package com.prabal.weather.presentation.weather_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.prabal.weather.data.remote.dto.CurrentConditions
import com.prabal.weather.data.remote.dto.NextDay
import com.prabal.weather.presentation.ui.theme.PrimaryBackground
import com.prabal.weather.presentation.ui.theme.White

@ExperimentalComposeUiApi
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    navController: NavController,
    location: String
) {
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            TopAppBar(
                name = location,
                onClick = {
                    navController.navigateUp()
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                state.weather?.let { weather ->
                    DetailCard(
                        region = weather.region,
                        currentConditions = weather.currentConditions,
                        onRefresh = {
                            viewModel.getWeather(location)
                        }
                    )
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(weather.next_days) { nextDay ->
                            NextDaysDataCard(nextDay = nextDay)
                        }
                    }
                }
            }
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun TopAppBar(
    name: String,
    onClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        backgroundColor = PrimaryBackground
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onClick,
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = name,
                modifier = Modifier,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun DetailCard(
    region: String,
    currentConditions: CurrentConditions,
    onRefresh: () -> Unit
) {
    Card(
        backgroundColor = White,
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = region,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(2f)
                )
                Image(
                    painter = rememberImagePainter(currentConditions.iconURL),
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${currentConditions.temp.c}°C",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier
                        .weight(2f)
                )
                Column {
                    Text(text = "Precipitation: ${currentConditions.precip}")
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Humidity: ${currentConditions.humidity}")
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Wind: ${currentConditions.wind.km}/km")
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = currentConditions.comment)
                }
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Last update: ${currentConditions.dayhour}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.15.sp,
                    modifier = Modifier
                        .weight(3.5f)
                )
                IconButton(
                    onClick = {
                        onRefresh()
                    },
                    modifier = Modifier
                        .weight(.5f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }
        }
    }
}

@Composable
fun NextDaysDataCard(
    nextDay: NextDay
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        backgroundColor = White,
        elevation = 4.dp,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = nextDay.day
            )
            Image(
                painter = rememberImagePainter(nextDay.iconURL),
                contentDescription = nextDay.comment,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(
                modifier = Modifier
                    .height(5.dp)
            )
            Text(
                text = nextDay.comment,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .height(5.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${nextDay.max_temp.c}°C / ${nextDay.min_temp.c}°C"
                )
            }
        }
    }
}
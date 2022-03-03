package com.prabal.weather.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.prabal.weather.R
import com.prabal.weather.data.remote.dto.CurrentConditions
import com.prabal.weather.data.remote.dto.NextDay
import com.prabal.weather.data.remote.dto.Temp
import com.prabal.weather.data.remote.dto.Wind
import com.prabal.weather.presentation.ui.theme.*
import com.prabal.weather.presentation.weather_detail.WeatherViewModel

@ExperimentalComposeUiApi
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchBox()
            Spacer(modifier = Modifier.height(16.dp))
            state.weather?.let { weather ->
                DetailCard(
                    region = weather.region,
                    currentConditions = weather.currentConditions
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

@ExperimentalComposeUiApi
@Composable
fun SearchBox(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    var text by remember { mutableStateOf("") }
    val location = viewModel.location.value.location
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = {
            text = it
            viewModel.getLocation(it)
        },
        label = null,
        placeholder = { Text(text = "Enter your location") },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp, bottom = 8.dp)
            .clip(SearchShape.medium)
            .background(color = White)
            .border(width = 1.dp, color = Black, shape = SearchShape.medium),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "",
                tint = LightText,
                modifier = Modifier.size(20.dp)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                viewModel.getWeather(location = location)
                keyboardController?.hide()
            }
        )
    )
}

@Composable
fun DetailCard(
    region: String,
    currentConditions: CurrentConditions,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val location = viewModel.location.value.location
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
                              viewModel.getWeather(location)
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

@Preview
@Composable
fun NextDayDataCardPreview() {
    WeatherTheme {
        NextDaysDataCard(
            nextDay = NextDay(
                day = "Tuesday",
                comment = "Clear with periodic clouds",
                max_temp = Temp(
                    c = 30,
                    f = 86
                ),
                min_temp = Temp(
                    c = 18,
                    f = 65
                ),
                iconURL = "https://ssl.gstatic.com/onebox/weather/48/sunny_s_cloudy.png"
            )
        )
    }
}

@Preview
@Composable
fun DetailCardPreview() {
    WeatherTheme {
        DetailCard(
            region = "Bankura, West Bengal, India",
            currentConditions = CurrentConditions(
                dayhour = "Monday, 9:00 pm",
                temp = Temp(c = 22, f = 72),
                precip = "1%",
                humidity = "73%",
                wind = Wind(km = 0, mile = 0),
                iconURL = "https://ssl.gstatic.com/onebox/weather/64/sunny.png",
                comment = "Mostly sunny"
            )
        )
    }
}
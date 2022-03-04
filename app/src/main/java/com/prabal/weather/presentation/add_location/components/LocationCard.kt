package com.prabal.weather.presentation.add_location.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prabal.weather.data.local.entity.LocationEntity
import com.prabal.weather.presentation.ui.theme.PrimaryBackground

@Composable
fun LocationCard(
    locationEntity: LocationEntity,
    onClick: () -> Unit,
    onItemClick: (LocationEntity) -> Unit
) {
    Card(
       backgroundColor = PrimaryBackground,
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp,
        modifier = Modifier.clickable { onItemClick(locationEntity) }
    ) {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(30.dp),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Text(
               text = locationEntity.locationName,
               fontWeight = FontWeight.Medium,
               fontSize = 20.sp
           )
           IconButton(
               onClick = onClick,
               modifier = Modifier
                   .size(40.dp)
           ) {
               Icon(
                   imageVector = Icons.Default.Delete,
                   contentDescription = "Delete"
               )
           }
       }
    }
}
package com.example.saki.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.saki.domain.models.DeviceDataModel
import com.example.saki.domain.models.ListItemDataModel
import com.example.saki.ui.theme.LogoBlue
import com.example.saki.ui.viewmodels.FirebaseViewModel

@Preview
@Composable
fun DeviceThumbnails(Data:DeviceDataModel= DeviceDataModel(), selected:MutableState<Boolean> = mutableStateOf(false), Thumbnails:List<ListItemDataModel> = emptyList(), navigable:Boolean = false,
                     navHostController: NavController? = null, firebaseViewModel: FirebaseViewModel = viewModel()) {
    ElevatedCard( modifier = Modifier
        .height(200.dp)
        .width(150.dp)
        .padding(10.dp)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(color = LogoBlue)
        ) {
            if (!navigable) {
                Thumbnails.forEach {
                    it.selected.value = it.id == Data.id
                }
            } else {
                firebaseViewModel.selectedDevice = Data
                navHostController!!.navigate("devicestate")
            }
        },
        colors = CardDefaults.cardColors(containerColor = if (!selected.value) Color.White else LogoBlue ),
        elevation = CardDefaults.cardElevation(5.dp),

    ) {
            Column( modifier = Modifier.fillMaxSize(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                ElevatedCard( modifier = Modifier
                    .height(72.dp)
                    .width(72.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(3.dp)) {
                    AsyncImage(model = Data.icon , contentDescription = "Icon" ,
                        modifier = Modifier.fillMaxSize(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = Data.name, fontSize = 16.sp, textAlign = TextAlign.Center, fontWeight = FontWeight(500), color = if (!selected.value) Color.Black else Color.White)
                Text(text = Data.type, fontSize = 16.sp, textAlign = TextAlign.Center, fontWeight = FontWeight(300), color = if (!selected.value) Color.Gray else Color.White)

            }
    }
}
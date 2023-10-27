package com.example.saki.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.saki.ui.theme.LogoBlue

@Preview
@Composable
fun ItemCreationPage(navHostController: NavHostController = NavHostController(LocalContext.current)) {
    Surface( color = LogoBlue ){
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column( modifier = Modifier.fillMaxWidth(1f) ) {
                Text(text = "Create", fontSize = 64.sp, color = White, modifier = Modifier.fillMaxWidth(1f))
                Text(text = "Select the type of create operation to perform", fontSize = 22.sp, color = White, fontWeight = FontWeight(200), modifier = Modifier.fillMaxWidth(1f))
            }
            Spacer(modifier = Modifier.height(50.dp))
            Column( modifier = Modifier.fillMaxWidth(1f) ) {


                ElevatedCard( modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(160.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = LogoBlue)
                    ) {
                        navHostController.navigate("createdevice")
                    },
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(15.dp,0.dp)) {

                    Row( modifier = Modifier
                        .fillMaxSize(1f)
                        .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly) {

                        Icon(Icons.Filled.Add, "Device Icon",
                            modifier = Modifier
                                .height(64.dp)
                                .width(64.dp),
                            tint = LogoBlue)

                        Divider( modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .padding(0.dp,15.dp))

                        Column( modifier = Modifier.padding(10.dp).width(200.dp) ) {
                            Text(text = "Device", fontSize = 24.sp, color = LogoBlue)
                            Text(text = "Add a new device to your network", fontSize = 18.sp, color = Gray)
                        }

                    }

                }

                Spacer(modifier = Modifier.height(20.dp))
                ElevatedCard( modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(160.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = LogoBlue)
                    ) {
                      navHostController.navigate("createdevicedefinition")
                    },
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(15.dp,0.dp)) {

                    Row( modifier = Modifier
                        .fillMaxSize(1f)
                        .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly) {

                        Icon(Icons.Filled.Create, "Device Def Icon",
                            modifier = Modifier
                                .height(64.dp)
                                .width(64.dp),
                            tint = LogoBlue)

                        Divider( modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .padding(0.dp,15.dp))

                        Column( modifier = Modifier.padding(10.dp).width(200.dp) ) {
                            Text(text = "Device Definition", fontSize = 24.sp, color = LogoBlue)
                            Text(text = "Create a definition for a new type of device", fontSize = 18.sp, color = Gray)
                        }

                    }

                }
            }
        }
    }
}


package com.entree.entreeapp.presentation.sportfacilities

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.entree.entreeapp.R
import com.entree.entreeapp.components.TopBar
import com.entree.entreeapp.presentation.profile.ProfileViewModel
import com.entree.entreeapp.presentation.sportfacilities.SportFacilityViewModel
import com.entree.entreeapp.presentation.sportfacilities.TicketTypeViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SportFacilityView(vm: SportFacilityViewModel,
                      navcontroller: NavController, viewModel: ProfileViewModel = hiltViewModel()

) {

    //val navController = rememberNavController()
    LaunchedEffect(Unit, block = {
        vm.getSportFacilityList()

    })

    Scaffold(
        topBar = {
            TopBar(

                title = "Sport Fascilities",

                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content = {
            if (vm.errorMessage.isEmpty()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(vm.sportFacilityList) { sportFacility ->
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,

                                    ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(0.dp, 0.dp, 16.dp, 0.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(
                                        ){
                                            Surface(
                                                modifier = Modifier
                                                    .size(54.dp)
                                                    .padding(5.dp),
                                                shape = CircleShape,
                                                border = BorderStroke(0.5.dp, Color.LightGray),
                                                elevation = 4.dp,
                                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.gymicon),
                                                    contentDescription = "profile image",
                                                    modifier = Modifier.size(35.dp),
                                                    contentScale = ContentScale.Crop
                                                )

                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(
                                                sportFacility.name,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.weight(1f).padding(0.dp, 5.dp)
                                            )
                                            Spacer(modifier = Modifier.width(75.dp))
                                            Button(onClick = {
                                                navcontroller.navigate(route = "sportfacility_details/"+sportFacility.id)
                                            }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(
                                                Color.Black)) {
                                                Text(text="Megnézem", color= Color.White, fontSize = 10.sp);
                                            }
                                        }
                                    }
                                }
                                Divider()
                            }
                        }
                    }
                }
            } else {
                Text(vm.errorMessage)
            }
        },
        )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SportFacilityDetails(id:Int?,
                         vm: TicketTypeViewModel, viewModel: ProfileViewModel = hiltViewModel(), navcontroller: NavController
) {
    LaunchedEffect(Unit, block = {
        vm.getTicketTypeList(id)
    })
    Scaffold(
        topBar = {
            TopBar(

                title = "Ticket types",

                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }

            )
        },
        content = {
            if (vm.errorMessage.isEmpty()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Napijegyek", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    LazyColumn(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)) {
                        items(vm.ticketTypeList_Daily) { ticketType ->
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(0.dp, 0.dp, 16.dp, 0.dp)
                                    ) {
                                        Row() {
                                            Column(modifier = Modifier.weight(1f)){
                                                Text(
                                                    ticketType.name,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,

                                                    )
                                                Text(
                                                    text="Ár: "+ ticketType.price,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,

                                                    )
                                            }
                                            Spacer(modifier = Modifier.width(45.dp))
                                            Button(onClick = {
                                                navcontroller.navigate(route = "checkout/"+ticketType.id+"/"+ticketType.price)
                                            }, colors = ButtonDefaults.buttonColors(Color.Black), modifier = Modifier.weight(1f)) {
                                                Text(text="Vásárlás", color= Color.White );
                                            }
                                        }

                                    }

                                }
                                Divider()
                            }
                        }
                    }

                    Text(text = "Bérletek", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    LazyColumn(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)) {
                        items(vm.ticketTypeList_Monthly) { ticketType ->
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(0.dp, 0.dp, 16.dp, 0.dp)
                                    ) {
                                        Row() {
                                            Column(modifier = Modifier.weight(1f)){
                                                Text(
                                                    ticketType.name,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis ,

                                                    )
                                                Text(
                                                    text="Ár: "+ ticketType.price,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(45.dp))
                                            Button(onClick = {
                                                navcontroller.navigate(route = "checkout/"+ticketType.id+"/"+ticketType.price)
                                            }, colors = ButtonDefaults.buttonColors(Color.Black), modifier = Modifier.weight(1f)) {
                                                Text(text="Vásárlás", color= Color.White);

                                            }
                                        }
                                    }
                                }
                                Divider()
                            }
                        }
                    }
                    Text(text = "Csoportos Órák", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    LazyColumn(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)) {
                        items(vm.ticketTypeList_Group) { ticketType ->
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(0.dp, 0.dp, 16.dp, 0.dp)
                                    ) {
                                        Row() {
                                            Column(modifier = Modifier.weight(1f)){
                                                Text(
                                                    ticketType.name,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,

                                                    )
                                                Text(
                                                    text="Ár: "+ ticketType.price,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(45.dp))
                                            Button(onClick = {
                                                navcontroller.navigate(route = "checkout/"+ticketType.id+"/"+ticketType.price)
                                            } , colors = ButtonDefaults.buttonColors(Color.Black), modifier = Modifier.weight(1f)) {
                                                Text(text="Vásárlás", color = Color.White);

                                            }
                                        }
                                    }
                                }
                                Divider()
                            }
                        }
                    }
                    Text(
                        text = "Személyi Edzés",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    LazyColumn(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)) {
                        items(vm.ticketTypeList_Trainer) { ticketType ->
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(0.dp, 0.dp, 16.dp, 0.dp)
                                    ) {
                                        Row() {
                                            Column(modifier = Modifier.weight(1f)){
                                                Text(
                                                    ticketType.name,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,

                                                    )
                                                Text(
                                                    text="Ár: "+ ticketType.price,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(45.dp))
                                            Button(onClick = {
                                                navcontroller.navigate(route = "checkout/"+ticketType.id+"/"+ticketType.price)
                                            }, colors = ButtonDefaults.buttonColors(Color.Black), modifier = Modifier.weight(1f)) {
                                                Text(text="Vásárlás", color= Color.White);

                                            }
                                        }
                                    }
                                }
                                Divider()
                            }
                        }
                    }
                }
            } else {
                Text(vm.errorMessage)
            }
        }
    )
}
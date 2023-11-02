package com.entree.entreeapp.presentation.admin_site

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.entree.entreeapp.R
import com.entree.entreeapp.apiservice.SportFacility
import com.entree.entreeapp.components.TopBar
import com.entree.entreeapp.enums.TicketTypeDetailNavigateType
import com.entree.entreeapp.presentation.profile.ProfileViewModel
import com.entree.entreeapp.presentation.sportfacilities.SportFacilityViewModel
import com.entree.entreeapp.screen.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun AdminScreen(viewModel: ProfileViewModel = hiltViewModel(), avm: AdminScreenViewModel, navcontroller: NavController){
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit, block = {
        isLoading = true
        avm.getSportFacilityByAdminEmail(FirebaseAuth.getInstance().currentUser?.email)
        isLoading = false
    })

    Scaffold(
        topBar = {
            TopBar(
                title = "Admin felület",

                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content={
            if (isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        color = Color(0xff91a4fc),
                        modifier = Modifier.size(32.dp),
                    )
                }
            } else {
                if (avm.errorMessage.isEmpty()) {
                    var sportFacility:SportFacility by remember{mutableStateOf(SportFacility(id = avm.sportFacilityDetails.id, name=avm.sportFacilityDetails.name, site=avm.sportFacilityDetails.site))}
                    var inputName:String by remember{mutableStateOf(avm.sportFacilityDetails.name)}
                    var inputSite:String by remember{mutableStateOf(avm.sportFacilityDetails.site)}

                    Column (modifier=Modifier.padding(10.dp)){
                        Row(){
                            Column{
                                Text(
                                    text="Edzőterem alapadatai",
                                    fontWeight= FontWeight.Bold,
                                    fontSize=25.sp
                                )
                                Spacer(modifier=Modifier.padding(8.dp))
                                Text(
                                    text="Név:"
                                )
                                TextField(
                                    value = inputName,
                                    onValueChange = {
                                        inputName=it
                                        sportFacility.name=it
                                    },
                                    modifier=Modifier.fillMaxWidth()
                                )
                                Text(
                                    text="Cím:"
                                )
                                TextField(
                                    value = inputSite,
                                    onValueChange = {
                                        inputSite=it
                                        sportFacility.site=it
                                    },
                                    modifier=Modifier.fillMaxWidth()
                                )
                                Spacer(modifier=Modifier.padding(8.dp))
                                Button(
                                    onClick = {
                                        CoroutineScope(Dispatchers.Default).launch {
                                            avm.updateSportFacility(sportFacility)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(Color.Black),
                                ) {
                                    Text(text = "Mentés", color=Color.White)
                                }
                                Spacer(modifier=Modifier.padding(8.dp))
                            }
                        }

                        Text(
                            text="Jegytípusok",
                            fontWeight= FontWeight.Bold,
                            fontSize=25.sp
                        )
                        Surface(modifier = Modifier.padding(all = Dp(5f)).weight(1f)) {
                            LazyColumn {
                                items(avm.sportFacilityDetails.ticketTypes) { ticketType ->
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
                                                ) {
                                                    Column(modifier = Modifier.weight(1f)) {
                                                        Text(
                                                            ticketType.name,
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis,
                                                            color = Color.Black
                                                        )
                                                        Text(
                                                            text = "Ár: " + ticketType.price + "Ft",
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis,
                                                            color = Color.Black
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.width(25.dp))
                                                    IconButton(onClick = {navcontroller.navigate(route = "admin_ticketType_details/"+ ticketType.id+"/"+ TicketTypeDetailNavigateType.VIEW.value) }) {
                                                        Icon(painter = painterResource(R.drawable.baseline_preview_24), contentDescription = "Ticket Icon")
                                                    }
                                                    Spacer(modifier = Modifier.width(25.dp))

                                                    IconButton(onClick = { navcontroller.navigate(route = "admin_ticketType_details/"+ ticketType.id+"/"+ TicketTypeDetailNavigateType.EDIT.value) }) {
                                                        Icon(painter = painterResource(R.drawable.baseline_edit_24), contentDescription = "Edit Icon")
                                                    }
                                                    Spacer(modifier = Modifier.width(25.dp))
                                                    IconButton(onClick = { }) {
                                                        Icon(painter = painterResource(R.drawable.baseline_delete_24), contentDescription = "Delete Icon")
                                                    }
                                                }
                                            }
                                        }
                                        Divider()
                                    }
                                }
                            }

                        }
                        Spacer(modifier=Modifier.padding(2.dp))
                        Button(
                            onClick = {
                                navcontroller.navigate(route = "admin_ticketType_details/"+ null+"/"+ TicketTypeDetailNavigateType.NEW.value)
                            },
                            colors = ButtonDefaults.buttonColors(Color.Black),
                        ) {
                            Text(text = "Új felvétele",color=Color.White)
                        }
                        Spacer(modifier=Modifier.padding(8.dp))
                        Text(
                            text="Edzők",
                            fontWeight= FontWeight.Bold,
                            fontSize=25.sp
                        )
                        Surface(modifier = Modifier.padding(all = Dp(5f)).weight(1f)) {
                            LazyColumn {
                                items(avm.sportFacilityDetails.trainers) { trainer ->
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
                                                ) {
                                                    Column(modifier = Modifier.weight(1f)) {
                                                        Text(
                                                            trainer.name,
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis,
                                                            color = Color.Black
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.width(25.dp))
                                                    IconButton(onClick = { }) {
                                                        Icon(painter = painterResource(R.drawable.baseline_preview_24), contentDescription = "Ticket Icon")
                                                    }
                                                    Spacer(modifier = Modifier.width(25.dp))

                                                    IconButton(onClick = {  }) {
                                                        Icon(painter = painterResource(R.drawable.baseline_edit_24), contentDescription = "Edit Icon")
                                                    }
                                                    Spacer(modifier = Modifier.width(25.dp))
                                                    IconButton(onClick = { }) {
                                                        Icon(painter = painterResource(R.drawable.baseline_delete_24), contentDescription = "Delete Icon")
                                                    }
                                                }
                                            }
                                        }
                                        Divider()
                                    }
                                }
                            }
                        }
                        Spacer(modifier=Modifier.padding(2.dp))
                        Button(
                            onClick = {
                                CoroutineScope(Dispatchers.Default).launch {

                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color.Black),
                        ) {
                            Text(text = "Új felvétele", color=Color.White)
                        }
                    }
                }
                else{
                    Text(avm.errorMessage)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color(0xff91a4fc),
                shape = CircleShape,
                onClick = {
                    isLoading = true
                    CoroutineScope(Dispatchers.Default).launch {
                        avm.getSportFacilityByAdminEmail(FirebaseAuth.getInstance().currentUser?.email)
                        isLoading = false
                    }
                },
                modifier = Modifier
                    .padding(32.dp, 48.dp )
                    .zIndex(2f),
            ) {
                Icon(Icons.Default.Refresh,contentDescription = "content description", tint = Color.White)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.End,
    )
}
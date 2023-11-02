package com.entree.entreeapp.presentation.sportfacilities

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.entree.entreeapp.R
import com.entree.entreeapp.components.TopBar
import com.entree.entreeapp.presentation.profile.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SportFacilityView(vm: SportFacilityViewModel,
                      navcontroller: NavController, viewModel: ProfileViewModel = hiltViewModel()

) {
    var isLoading by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    LaunchedEffect(Unit, block = {
        isLoading = true
        vm.getSportFacilityList()
        isLoading = false
    })

    Scaffold(
        topBar = {
            TopBar(
                title = "Edzőtermek",

                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content = {
            Image(
                painter = painterResource(id = R.drawable.backgroundimage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.8F,
            )
            Column(modifier = Modifier.padding(16.dp), ) {
                SearchBar(query = query, onQueryChange = { newQuery ->
                    query = newQuery
                })
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
                    if (vm.errorMessage.isEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .zIndex(1f)
                        ) {
                            items(vm.sportFacilityList.filter { item -> query.isEmpty() || item.name.lowercase().contains(query.lowercase())}) { sportFacility ->
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
                                                Surface(
                                                    modifier = Modifier
                                                        .size(54.dp)
                                                        .padding(5.dp),
                                                    shape = CircleShape,
                                                    elevation = 4.dp,
                                                    color = Color.White.copy(alpha=0.5f)
                                                ) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.gymicon),
                                                        contentDescription = "gym icon",
                                                        modifier = Modifier.size(35.dp),
                                                        contentScale = ContentScale.Crop,
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Text(
                                                    sportFacility.name,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(0.dp, 5.dp),
                                                    color = Color.White,
                                                )
                                                Spacer(modifier = Modifier.width(75.dp))
                                                Button(
                                                    onClick = {
                                                        navcontroller.navigate(route = "sportfacility_details/" + sportFacility.id)
                                                    },
                                                    modifier = Modifier.weight(1f),
                                                    colors = ButtonDefaults.buttonColors(
                                                        Color.Black
                                                    )
                                                ) {
                                                    Text(
                                                        text = "Megnézem",
                                                        color = Color.White,
                                                        fontSize = 10.sp
                                                    );
                                                }
                                            }
                                        }
                                    }
                                    Divider()
                                }
                            }
                        }
                    } else {
                        Text(vm.errorMessage,
                        color=Color.White)
                    }
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color(0xff91a4fc),
                shape = CircleShape,
                onClick = {
                    isLoading = true
                    query=""
                    CoroutineScope(Dispatchers.Default).launch {
                        vm.getSportFacilityList()
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

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        value = query,
        onValueChange = { newText ->
            onQueryChange(newText)
        },
        placeholder = {
            Text(text = "Kereső...")
        },
        singleLine = true,
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SportFacilityDetails(id:Int?,
                         vm: TicketTypeViewModel, viewModel: ProfileViewModel = hiltViewModel(), navcontroller: NavController
) {
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit, block = {
        isLoading = true
        vm.getTicketTypeList(id)
        isLoading = false
    })
    Scaffold(
        topBar = {
            TopBar(
                title = "Kategóriák",

                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content = {
            Image(
                painter = painterResource(id = R.drawable.backgroundimage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.8F,
            )
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
            }
            else {
                if (vm.errorMessage.isEmpty()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Edzés egyénileg",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        ) {
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
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        ticketType.name,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        color = Color.White
                                                    )
                                                    Text(
                                                        text = "Ár: " + ticketType.price + " Ft",
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        color = Color.White
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(45.dp))
                                                Button(
                                                    onClick = {
                                                        navcontroller.navigate(route = "checkout/" + ticketType.id + "/" + ticketType.price)
                                                    },
                                                    colors = ButtonDefaults.buttonColors(Color.Black),
                                                    modifier = Modifier.weight(1f)
                                                ) {
                                                    Text(text = "Vásárlás", color = Color.White);
                                                }
                                            }

                                        }

                                    }
                                    Divider()
                                }
                            }
                        }
                        Text(
                            text = "Edzés edzővel",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        ) {
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
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        ticketType.name,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        color = Color.White
                                                    )
                                                    Text(
                                                        text = "Ár: " + ticketType.price,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        color = Color.White
                                                    )
                                                }

                                                Spacer(modifier = Modifier.width(45.dp))
                                                Button(
                                                    onClick = {
                                                        navcontroller.navigate(route = "checkout/" + ticketType.id + "/" + ticketType.price)
                                                    },
                                                    colors = ButtonDefaults.buttonColors(Color.Black),
                                                    modifier = Modifier.weight(1f)
                                                ) {
                                                    Text(text = "Vásárlás", color = Color.White);
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
                    Text(vm.errorMessage, color = Color.White)
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
                        vm.getTicketTypeList(id)
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
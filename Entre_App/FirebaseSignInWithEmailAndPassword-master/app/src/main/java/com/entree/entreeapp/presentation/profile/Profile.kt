package com.entree.entreeapp.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.entree.entreeapp.R
import com.entree.entreeapp.components.TopBar
import com.entree.entreeapp.presentation.profile.ProfileViewModel
import com.entree.entreeapp.presentation.sportfacilities.TicketTypeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyProfile(viewModel: ProfileViewModel = hiltViewModel(), ticketTypeViewModel: TicketTypeViewModel, navcontroller: NavHostController){
    Scaffold(
        topBar = {
            TopBar(

                title = "Fiókom",

                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content = {

            Box(){
                CreateProfileCard(ticketTypeViewModel=ticketTypeViewModel, navcontroller=navcontroller)
            }
        })
}

@Composable
private fun CreateImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .size(114.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile image",
            modifier = modifier.size(95.dp),
            contentScale = ContentScale.Crop
        )

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun CreateInfo(ticketTypeViewModel: TicketTypeViewModel, navcontroller: NavHostController) {
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit, block = {
        isLoading=true
        ticketTypeViewModel.getTicketsByUid(FirebaseAuth.getInstance().currentUser?.uid)
        isLoading=false
    })

    var email= FirebaseAuth.getInstance().currentUser?.email?.let { it1 -> Text(it1) }
    Column(
        modifier = Modifier
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            color = Color.Blue,
            fontSize = 24.sp,
            style = MaterialTheme.typography.h4,
            text = "Pongrácz Dávid"
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Megvásárolt jegyek", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Scaffold(
                content = {
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
                    if (ticketTypeViewModel.errorMessage.isEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        ) {
                            items(ticketTypeViewModel.ticketsofGuest) { ticket ->
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
                                                    if (ticket.typeName != null) {
                                                        Text(
                                                            text = ticket.typeName as String,
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis,
                                                        )
                                                    }
                                                }
                                                Spacer(modifier = Modifier.width(45.dp))
                                                Button(
                                                    onClick = {
                                                        navcontroller.navigate(route = "qrcode")
                                                    },
                                                    colors = ButtonDefaults.buttonColors(Color.Black),
                                                    modifier = Modifier.weight(1f)
                                                ) {
                                                    Text(text = "Beolvasás", color = Color.White);
                                                }
                                            }
                                        }
                                    }
                                    Divider()
                                }
                            }
                        }
                    } else {
                        Text(ticketTypeViewModel.errorMessage)
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
                                ticketTypeViewModel.getTicketsByUid(FirebaseAuth.getInstance().currentUser?.uid)
                                isLoading = false
                            }
                        },
                        modifier = Modifier
                            .padding(24.dp, 40.dp )
                            .zIndex(2f),
                    ) {
                        Icon(Icons.Default.Refresh,contentDescription = "content description", tint = Color.White)
                    }
                },
                isFloatingActionButtonDocked = true,
                floatingActionButtonPosition = FabPosition.End,
            )
        }
    }
}

@Composable
fun CreateProfileCard(ticketTypeViewModel: TicketTypeViewModel, navcontroller: NavHostController) {
    val buttonClickedState = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() ,
        color=Color.LightGray
    )
    {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(330.dp)
                .padding(12.dp),

            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            backgroundColor = Color.White,
            elevation = 4.dp,
        )
        {
            Column(
                modifier = Modifier.height(250.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateImageProfile()
                Divider()
                CreateInfo(ticketTypeViewModel = ticketTypeViewModel, navcontroller=navcontroller)
            }
        }
    }
}
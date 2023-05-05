package ro.alexmamo.firebasesigninwithemailandpassword.myapplication

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.TopBar
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.ProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyProfile(viewModel: ProfileViewModel = hiltViewModel(), ticketTypeViewModel: TicketTypeViewModel){

    LaunchedEffect(Unit, block = {
        ticketTypeViewModel.getTicketsByEmail(FirebaseAuth.getInstance().currentUser?.email)
    })

    Scaffold(
        topBar = {
            TopBar(

                title = "My Profile",

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
                CreateProfileCard(ticketTypeViewModel=ticketTypeViewModel)

            }
        })
}

@Composable
private fun CreateImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .size(154.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.defaultprofile),
            contentDescription = "profile image",
            modifier = modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )

    }
}

@Composable
private fun CreateInfo(ticketTypeViewModel: TicketTypeViewModel) {
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
            text = "Name"
        )

        Text(
            text = "Androdi Compose Programmer",
            modifier = Modifier.padding(3.dp)
        )

        Text(
            text = "@JetpackCompose",
            modifier = Modifier.padding(3.dp),
            style = MaterialTheme.typography.subtitle1
        )
        if (ticketTypeViewModel.errorMessage.isEmpty()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Megvásárolt jegyek", fontSize = 30.sp, fontWeight = FontWeight.Bold)
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
                                            if(ticket.typename!=null){
                                                Text(
                                                    text=ticket.typename as String,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    )
                                            }


                                        }
                                        Spacer(modifier = Modifier.width(45.dp))
                                        Button(
                                            onClick = {

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
            }
        }
        else {
            Text(ticketTypeViewModel.errorMessage)
        }


    }
}

@Composable
fun CreateProfileCard(ticketTypeViewModel: TicketTypeViewModel) {
    val buttonClickedState = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
    {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(390.dp)
                .padding(12.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            backgroundColor = Color.White,
            elevation = 4.dp
        )
        {
            Column(
                modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateImageProfile()
                Divider()
                CreateInfo(ticketTypeViewModel = ticketTypeViewModel)

            }
        }
    }

}
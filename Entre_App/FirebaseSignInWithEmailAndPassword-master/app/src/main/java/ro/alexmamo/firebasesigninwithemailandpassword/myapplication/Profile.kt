package ro.alexmamo.firebasesigninwithemailandpassword.myapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.TopBar
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.ProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyProfile(viewModel: ProfileViewModel = hiltViewModel()){

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
                CreateProfileCard()

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
private fun CreateInfo() {
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
    }
}

@Composable
fun CreateProfileCard() {
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
                CreateInfo()

            }
        }
    }

}
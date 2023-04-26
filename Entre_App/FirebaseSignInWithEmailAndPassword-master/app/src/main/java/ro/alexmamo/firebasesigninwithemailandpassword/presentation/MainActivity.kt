package ro.alexmamo.firebasesigninwithemailandpassword.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ro.alexmamo.firebasesigninwithemailandpassword.components.TopBar
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.NavGraph
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.myapplication.*
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.ProfileViewModel

@AndroidEntryPoint
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberAnimatedNavController()
            NavGraph(
                navController = navController,
                spfvm = SportFacilityViewModel(),
                ttvm=TicketTypeViewModel(),
                uvm= UserViewModel()
            )
            AuthState()
        }
    }

    @Composable
    private fun AuthState() {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            if (viewModel.isEmailVerified) {
                NavigateToProfileScreen()
            } else {
                NavigateToVerifyEmailScreen()
            }
        }
    }

    @Composable
    private fun NavigateToSignInScreen() = navController.navigate(SignInScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToProfileScreen() = navController.navigate(ProfileScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToVerifyEmailScreen() = navController.navigate(VerifyEmailScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navcontroller: NavHostController, sportFacilityViewModel: SportFacilityViewModel, ticketTypeViewModel: TicketTypeViewModel){
    Scaffold(bottomBar = {
        BottomBar(navController=navcontroller)
    }) {
        SetUpNavGraph(navController = navcontroller, spfvm= sportFacilityViewModel, ttvm= ticketTypeViewModel)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(backgroundColor = Color.DarkGray) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title, color=Color.White)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
                tint=Color.White
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
            }
        }
    )
}

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
                         vm: TicketTypeViewModel, viewModel: ProfileViewModel = hiltViewModel()
) {

    //val navController = rememberNavController()
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
                                                //navcontroller.navigate(route = "sportfacility_details/"+sportFacility.id)
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
                                                //navcontroller.navigate(route = "sportfacility_details/"+sportFacility.id)
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
                                                //navcontroller.navigate(route = "sportfacility_details/"+sportFacility.id)
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
                                                //navcontroller.navigate(route = "sportfacility_details/"+sportFacility.id)
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



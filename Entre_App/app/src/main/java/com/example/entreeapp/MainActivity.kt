package com.example.entreeapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {

    lateinit var navController:NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                navController= rememberNavController()
                HomeScreen(navcontroller = navController)
               // SetUpNavGraph(navController = navController, spfvm=SportFacilityViewModel(), ttvm= TicketTypeViewModel())
                //SportFacilityView(vm)
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navcontroller: NavHostController){
    Scaffold(bottomBar = {
        BottomBar(navController=navcontroller)
    }) {
        SetUpNavGraph(navController = navcontroller, spfvm=SportFacilityViewModel(), ttvm= TicketTypeViewModel())
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
        BottomNavigation {
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
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SportFacilityView(vm: SportFacilityViewModel,
                      navcontroller:NavController

) {

    //val navController = rememberNavController()
    LaunchedEffect(Unit, block = {
        vm.getSportFacilityList()
    })

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,

                title = {
                    Row {
                        Text(text="Sport Facilities", color=Color.White)
                    }
                })
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

                                                Text(
                                                    sportFacility.name,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                Spacer(modifier = Modifier.width(75.dp))
                                                Button(onClick = {
                                                            navcontroller.navigate(route = "sportfacility_details/"+sportFacility.id)
                                                }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color.Black)) {
                                                    Text(text="Megnézem", color=Color.White);

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
vm:TicketTypeViewModel
                         ) {

    //val navController = rememberNavController()
    LaunchedEffect(Unit, block = {
        vm.getTicketTypeList(id)
    })



    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                title = {
                    Row {
                        Text(text="Ticket Types", color=Color.White)
                    }
                })
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
                                                Text(text="Vásárlás", color=Color.White );

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
                                                Text(text="Vásárlás", color=Color.White);

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
                                                Text(text="Vásárlás", color=Color.White);

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

@Composable
fun Profile(){
    Box(){
        Text("Hello")
    }
}

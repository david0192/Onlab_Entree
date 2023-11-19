package com.entree.entreeapp.presentation.admin_site

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.entree.entreeapp.components.TopBar
import com.entree.entreeapp.enums.DetailNavigateType
import com.entree.entreeapp.models.TicketTypeDetails
import com.entree.entreeapp.models.TrainerDetails
import com.entree.entreeapp.presentation.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun TrainerDetails(viewModel: ProfileViewModel = hiltViewModel(), avm: AdminSiteViewModel, id:Int?, navigateTypeId:Int?){
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit, block = {
        isLoading = true
        avm.getTrainerById(id, FirebaseAuth.getInstance().currentUser?.uid)
        isLoading = false
    })

    Scaffold(
        topBar = {
            TopBar(
                title = "Edző",

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
                    var trainerDetails: TrainerDetails by remember{
                        mutableStateOf(
                        TrainerDetails(id = avm.trainerDetails.id, name=avm.trainerDetails.name, sportFacilityId = avm.trainerDetails.sportFacilityId, introduction = avm.trainerDetails.introduction)
                    )
                    }
                    var inputName:String by remember{ mutableStateOf(avm.trainerDetails.name) }
                    var inputIntroduction:String by remember{ mutableStateOf(avm.trainerDetails.introduction ?: "") }

                    Column (modifier= Modifier.padding(10.dp)){
                        Row(){
                            Column{
                                Text(
                                    text="Edző alapadatai",
                                    fontWeight= FontWeight.Bold,
                                    fontSize=25.sp
                                )
                                Spacer(modifier= Modifier.padding(8.dp))
                                Text(
                                    text="Név:"
                                )
                                TextField(
                                    value = inputName,
                                    onValueChange = {
                                        inputName=it
                                        trainerDetails.name=it
                                    },
                                    modifier= Modifier.fillMaxWidth(),
                                    readOnly = (navigateTypeId == DetailNavigateType.VIEW.value)
                                )
                                Spacer(modifier= Modifier.padding(8.dp))
                                Text(
                                    text="Bemutatkozás:"
                                )
                                TextField(
                                    value = inputIntroduction,
                                    onValueChange = {
                                        inputIntroduction=it
                                        trainerDetails.introduction=it
                                    },
                                    modifier= Modifier.fillMaxWidth()
                                        .height(100.dp)
                                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
                                    readOnly = (navigateTypeId == DetailNavigateType.VIEW.value)
                                )
                                Spacer(modifier= Modifier.padding(8.dp))
                                if(navigateTypeId!=DetailNavigateType.VIEW.value){
                                    Button(
                                        onClick = {
                                            CoroutineScope(Dispatchers.Default).launch {
                                                avm.createOrEditTrainer(trainerDetails)
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(Color.Black),
                                    ) {
                                        Text(text = "Mentés", color= Color.White)
                                    }
                                    Spacer(modifier= Modifier.padding(8.dp))
                                }
                            }
                        }
                    }

                    if(avm.updateSuccessFull){
                        Snackbar(
                            modifier = Modifier.padding(16.dp),
                            content = {
                                Text("Sikeres módósítás!")
                            },
                            action = {
                                TextButton(
                                    onClick = {
                                        avm.updateSuccessFull = false
                                    }
                                ) {
                                    Text("Rendben")
                                }
                            }
                        )
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
                        avm.getTrainerById(id, FirebaseAuth.getInstance().currentUser?.uid)
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
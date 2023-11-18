package com.entree.entreeapp.presentation.admin_site

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.entree.entreeapp.models.*
import com.entree.entreeapp.components.TopBar
import com.entree.entreeapp.enums.TicketTypeDetailNavigateType
import com.entree.entreeapp.presentation.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun TicketTypeEditDetails(viewModel: ProfileViewModel = hiltViewModel(), avm: AdminSiteViewModel, id:Int?, navigateTypeId:Int?){
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit, block = {
        isLoading = true
        avm.getTicketTypeById(id, FirebaseAuth.getInstance().currentUser?.uid)
        isLoading = false
    })

    Scaffold(
        topBar = {
            TopBar(
                title = "Jegytípus",

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
                        var ticketTypeDetails: TicketTypeDetails by remember{mutableStateOf(
                                TicketTypeDetails(id = avm.ticketTypeDetails.id, name=avm.ticketTypeDetails.name, categoryId = avm.ticketTypeDetails.categoryId, categoryValues = avm.ticketTypeDetails.categoryValues, maxUsages = avm.ticketTypeDetails.maxUsages,
                                    price = avm.ticketTypeDetails.price, sportFacilityId = avm.ticketTypeDetails.sportFacilityId, validityDays = avm.ticketTypeDetails.validityDays)
                        )}
                        var inputName:String by remember{ mutableStateOf(avm.ticketTypeDetails.name)}
                        var inputCategoryId:String by remember{ mutableStateOf(avm.ticketTypeDetails.categoryId.toString())}
                        var inputMaxUsages: String by remember{ mutableStateOf(avm.ticketTypeDetails.maxUsages?.toString() ?: "")}
                        var inputPrice: String by remember{ mutableStateOf(avm.ticketTypeDetails.price.toString())}
                        var inputValidityDays: String by remember{ mutableStateOf(avm.ticketTypeDetails.validityDays?.toString() ?: "")}

                    Column (modifier= Modifier.padding(10.dp)){
                        Row(){
                            Column{
                                Text(
                                    text="Jegytípus alapadatai",
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
                                        ticketTypeDetails.name=it
                                    },
                                    modifier=Modifier.fillMaxWidth(),
                                    readOnly = (navigateTypeId==TicketTypeDetailNavigateType.VIEW.value)
                                )
                                Spacer(modifier= Modifier.padding(8.dp))
                                Text(
                                    text="Kategória:"
                                )
                                val options = ticketTypeDetails.categoryValues.toList()
                                var expanded by remember { mutableStateOf(false) }
                                var selectedOptionText by remember { mutableStateOf(ticketTypeDetails.categoryValues.get(ticketTypeDetails.categoryId) ?:"") }

                                if(navigateTypeId==TicketTypeDetailNavigateType.VIEW.value){
                                    TextField(
                                        value = selectedOptionText,
                                        onValueChange = {
                                        },
                                        modifier=Modifier.fillMaxWidth(),
                                        readOnly = (navigateTypeId==TicketTypeDetailNavigateType.VIEW.value)
                                    )
                                }
                                else{
                                    ExposedDropdownMenuBox(
                                        expanded = expanded,
                                        onExpandedChange = {
                                            expanded = !expanded
                                        },
                                    ) {
                                        TextField(
                                            readOnly = true,
                                            value = selectedOptionText,
                                            onValueChange = {
                                            },
                                            label = { Text("Kategória") },
                                            trailingIcon = {
                                                ExposedDropdownMenuDefaults.TrailingIcon(
                                                    expanded = expanded
                                                )
                                            },
                                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                            modifier=Modifier.fillMaxWidth()
                                        )
                                        ExposedDropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = {
                                                expanded = false
                                            }
                                        ) {
                                            options.forEach { selectionOption ->
                                                DropdownMenuItem(
                                                    onClick = {
                                                        selectedOptionText = selectionOption.second
                                                        ticketTypeDetails.categoryId = selectionOption.first
                                                        expanded = false
                                                    }
                                                ){
                                                    Text(text = selectionOption.second)
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier= Modifier.padding(8.dp))
                                Text(
                                    text="Max felhasználási alkalom:"
                                )
                                TextField(
                                    value = inputMaxUsages,
                                    onValueChange = {
                                        inputMaxUsages = it
                                        val parsedNumber = it.toIntOrNull()
                                        if (parsedNumber != null) {
                                            ticketTypeDetails.maxUsages = parsedNumber
                                        } else {
                                            ticketTypeDetails.maxUsages= null
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                    modifier=Modifier.fillMaxWidth(),
                                    readOnly = (navigateTypeId==TicketTypeDetailNavigateType.VIEW.value)
                                )
                                Spacer(modifier= Modifier.padding(8.dp))
                                Text(
                                    text="Ár:"
                                )
                                TextField(
                                    value = inputPrice,
                                    onValueChange = {
                                        inputPrice = it
                                        val parsedNumber = it.toIntOrNull()
                                        if (parsedNumber != null) {
                                            ticketTypeDetails.price = parsedNumber
                                        } else {
                                            ticketTypeDetails.price=0
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                    modifier=Modifier.fillMaxWidth(),
                                    readOnly = (navigateTypeId==TicketTypeDetailNavigateType.VIEW.value)
                                )
                                Spacer(modifier= Modifier.padding(8.dp))
                                Text(
                                    text="Érvényes napok száma:"
                                )
                                TextField(
                                    value = inputValidityDays,
                                    onValueChange = {
                                        inputValidityDays = it
                                        val parsedNumber = it.toIntOrNull()
                                        if (parsedNumber != null) {
                                            ticketTypeDetails.validityDays = parsedNumber
                                        } else {
                                            ticketTypeDetails.validityDays = null
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                    modifier=Modifier.fillMaxWidth(),
                                    readOnly = (navigateTypeId==TicketTypeDetailNavigateType.VIEW.value)
                                )
                                Spacer(modifier= Modifier.padding(8.dp))
                                if(navigateTypeId!=TicketTypeDetailNavigateType.VIEW.value){
                                    Button(
                                        onClick = {
                                            CoroutineScope(Dispatchers.Default).launch {
                                                avm.createOrEditTicketType(ticketTypeDetails)
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
                        avm.getTicketTypeById(id, FirebaseAuth.getInstance().currentUser?.uid)
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
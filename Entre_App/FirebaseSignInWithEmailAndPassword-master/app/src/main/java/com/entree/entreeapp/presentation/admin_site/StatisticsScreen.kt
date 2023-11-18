package com.entree.entreeapp.presentation.admin_site

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.entree.entreeapp.models.*
import com.entree.entreeapp.components.TopBar
import com.entree.entreeapp.presentation.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StatisticsScreen(viewModel: ProfileViewModel = hiltViewModel(), avm: AdminSiteViewModel){
    var isLoading by remember { mutableStateOf(false) }
    var state=rememberDateRangePickerState()

    Scaffold(
        topBar = {
            TopBar(
                title = "Statisztika",

                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content={
            Column (modifier=Modifier.padding(10.dp).fillMaxSize()){
                Spacer(modifier=Modifier.padding(25.dp))
                Text(
                    text="Időszak:",
                    fontWeight= FontWeight.Bold,
                    fontSize=25.sp,
                    color = Color.Black,
                )
                Spacer(modifier=Modifier.padding(8.dp))
                Surface(modifier = Modifier
                    .padding(all = Dp(5f))
                    .weight(0.75f)) {
                    DateRangePicker(state=state,  modifier = Modifier.height(150.dp))
                }
                Button(
                    onClick = {
                        isLoading=true
                        CoroutineScope(Dispatchers.Default).launch {
                            avm.getSportFacilityStatistics(
                                SportFacilityStatisticsQuery(uid = FirebaseAuth.getInstance().currentUser?.uid, beginTime = state.selectedStartDateMillis?.let{ Date(it) }, endTime=state.selectedEndDateMillis?.let{ Date(it) }))
                            isLoading = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                ) {
                    Text(text = "Megnézem", color=Color.White)
                }
                Surface(modifier = Modifier
                    .padding(all = Dp(5f))
                    .weight(1f)) {
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
                    else{
                        if(avm.sportFacilityStatistics.ticketTypeBuyNumbers.isNotEmpty()){
                            if(avm.errorMessage.isEmpty()){
                                Column(){
                                    Text(
                                        text="Statisztika:",
                                        fontWeight= FontWeight.Bold,
                                        fontSize=25.sp
                                    )
                                    Spacer(modifier=Modifier.padding(8.dp))
                                    Row(
                                    ) {
                                        Text(
                                            text="Bevétel: ",
                                            fontWeight= FontWeight.Bold,
                                            fontSize=15.sp
                                        )
                                        Text(avm.sportFacilityStatistics.revenue.toString() +" Ft")
                                    }
                                    Spacer(modifier=Modifier.padding(4.dp))
                                    Text(
                                        text="Eladások:",
                                        fontWeight= FontWeight.Bold,
                                        fontSize=15.sp
                                    )
                                    LazyColumn {
                                        items(avm.sportFacilityStatistics.ticketTypeBuyNumbers.toList()) { ticketType ->
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
                                                    ) {
                                                        Row(
                                                        ) {
                                                            Text(
                                                                ticketType.first,
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis,
                                                                color = Color.Black,
                                                                textAlign = TextAlign.Start,
                                                            )
                                                            Spacer(modifier = Modifier.width(25.dp))
                                                            Text(
                                                                text =ticketType.second.toString() + " db",
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis,
                                                                color = Color.Black
                                                            )
                                                        }
                                                    }
                                                }
                                                androidx.compose.material.Divider()
                                            }
                                        }
                                    }
                                }
                            }
                            else{
                                Text(avm.errorMessage)
                            }
                        }
                    }
                }
            }
        },
    )

    @ExperimentalMaterial3Api
    @Composable
    fun DateRangePicker(
        state: DateRangePickerState,
        modifier: Modifier = Modifier,
        dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() },
        title: (@Composable () -> Unit)? = {
            DateRangePickerDefaults.DateRangePickerTitle(
                displayMode = state.displayMode,
                modifier = Modifier.padding(15.dp)
            )
        },
        headline: (@Composable () -> Unit)? = {
            DateRangePickerDefaults.DateRangePickerHeadline(
                selectedStartDateMillis = state.selectedStartDateMillis,
                selectedEndDateMillis = state.selectedEndDateMillis,
                displayMode = state.displayMode,
                dateFormatter,
                modifier = Modifier.padding(15.dp)
            )
        },
        showModeToggle: Boolean = true,
        colors: DatePickerColors = DatePickerDefaults.colors()
    ) {
    }
}
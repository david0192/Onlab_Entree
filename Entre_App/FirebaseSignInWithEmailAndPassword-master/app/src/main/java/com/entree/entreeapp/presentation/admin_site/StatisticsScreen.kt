package com.entree.entreeapp.presentation.admin_site

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.entree.entreeapp.apiservice.SportFacility
import com.entree.entreeapp.apiservice.SportFacilityStatisticsQuery
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
fun StatisticsScreen(viewModel: ProfileViewModel = hiltViewModel(), avm: AdminScreenViewModel){
    var isLoading by remember { mutableStateOf(true) }
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
            Column (modifier=Modifier.padding(10.dp)){
                Row(){
                    Column{
                        Text(
                            text="Időszak:",
                            fontWeight= FontWeight.Bold,
                            fontSize=25.sp
                        )
                        Spacer(modifier=Modifier.padding(8.dp))
                        Surface(modifier = Modifier.padding(all = Dp(5f)).weight(1f)) {
                            DateRangePicker(state=state,  modifier = Modifier.height(150.dp))

                        }
                        Button(
                            onClick = {
                                isLoading=true
                                CoroutineScope(Dispatchers.Default).launch {
                                    avm.getSportFacilityStatistics(
                                        SportFacilityStatisticsQuery(email = FirebaseAuth.getInstance().currentUser?.email, beginTime = state.selectedStartDateMillis?.let{ Date(it) }, endTime=state.selectedEndDateMillis?.let{ Date(it) }))
                                    isLoading = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color.Black),
                        ) {
                            Text(text = "Megnézem", color=Color.White)
                        }
                        Surface(modifier = Modifier.padding(all = Dp(5f)).weight(1f)) {

                        }
                    }
                }
                Row(){
                    if(avm.errorMessage.isEmpty()){
                        Text("Bevétel:"+ avm.sportFacilityStatistics.revenue)
                    }
                    else{
                        Text(avm.errorMessage)
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
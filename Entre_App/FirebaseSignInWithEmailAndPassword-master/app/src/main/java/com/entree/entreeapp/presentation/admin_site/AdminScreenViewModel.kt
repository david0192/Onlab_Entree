package com.entree.entreeapp.presentation.admin_site

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.entree.entreeapp.apiservice.*
import java.util.*

class AdminScreenViewModel:ViewModel() {
    var sportFacilityDetails: SportFacilityDetails by mutableStateOf(SportFacilityDetails(id=0, name = "", site = "", ticketTypes=listOf(), trainers=listOf()))
    var sportFacilityStatistics: SportFacilityStatisticsResult by mutableStateOf(SportFacilityStatisticsResult(revenue = 0, ticketTypeBuyNumbers= emptyMap()))
    var sportFacilityId: Int by mutableStateOf(0)
    var ticketTypeDetails: TicketTypeDetails by mutableStateOf(TicketTypeDetails(id = 0, name = "", categoryId = 0, categoryValues = emptyMap(), maxUsages = 0, price = 0, validityDays = 0 ))
    var errorMessage: String by mutableStateOf("")

    suspend fun getSportFacilityByAdminEmail(email: String?) {
        val apiService = APIService.getInstance()
        try {
            errorMessage=""
            sportFacilityDetails = apiService.getSportFacilityByAdminEmail(email)
            sportFacilityId = sportFacilityDetails.id
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun getSportFacilityStatistics(sportFacilityStatisticsQuery: SportFacilityStatisticsQuery) {
        val apiService = APIService.getInstance()
        try {
            errorMessage=""
            sportFacilityStatistics = apiService.getSportFacilityStatistics(sportFacilityStatisticsQuery)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun updateSportFacility(sportFacility: SportFacility) {
        val apiService = APIService.getInstance()
        try {
            errorMessage=""
            apiService.updateSportFacility(sportFacility)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun getTicketTypeById(int:Int?){
        val apiService = APIService.getInstance()
        try {
            errorMessage=""
            ticketTypeDetails = apiService.getTicketTypeById(int)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

}
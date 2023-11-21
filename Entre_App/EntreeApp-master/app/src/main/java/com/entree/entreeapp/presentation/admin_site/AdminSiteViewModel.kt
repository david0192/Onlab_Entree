package com.entree.entreeapp.presentation.admin_site

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import com.entree.entreeapp.models.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.entree.entreeapp.apiservice.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class AdminSiteViewModel @Inject constructor():ViewModel() {
    var sportFacilityDetails: SportFacilityDetails by mutableStateOf(SportFacilityDetails(
        id = 0, name = "", site = "", ticketTypes = listOf()
    , trainers = listOf()))

    var sportFacilityStatistics: SportFacilityStatisticsResult by mutableStateOf(SportFacilityStatisticsResult(revenue = 0, ticketTypeBuyNumbers= emptyMap()))

    var ticketTypeDetails: TicketTypeDetails by mutableStateOf(TicketTypeDetails(id = 0, name = "", categoryId = 0, categoryValues = emptyMap(), maxUsages = null, price = 0, sportFacilityId = 0,validityDays = null ))
    var trainerDetails: TrainerDetails by mutableStateOf(TrainerDetails(id = 0, name = "", sportFacilityId = 0, introduction = null))

    var errorMessage: String by mutableStateOf("")
    var updateSuccessFull:Boolean by mutableStateOf(false)
    var deleteSuccessFull:Boolean by mutableStateOf(false)

    suspend fun getSportFacilityByAdminUid(uid: String?) {
        val apiService = APIService.getInstance()
        try {
            errorMessage=""
            sportFacilityDetails = apiService.getSportFacilityByAdminUid(uid)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun getSportFacilityStatistics(sportFacilityStatisticsQuery: SportFacilityStatisticsQuery) {
        val apiService = APIService.getInstance()
        try {
            errorMessage=""
            sportFacilityStatistics = apiService.getSportFacilityStatistics(
                sportFacilityStatisticsQuery.uid, SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(sportFacilityStatisticsQuery.beginTime), SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(sportFacilityStatisticsQuery.endTime))
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun updateSportFacility(sportFacility: SportFacility) {
        val apiService = APIService.getInstance()
        try {
            updateSuccessFull=false
            errorMessage=""
            apiService.updateSportFacility(sportFacility)
            updateSuccessFull=true
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun getTicketTypeById(int:Int?, uid: String?){
        val apiService = APIService.getInstance()
        try {
            errorMessage=""
            ticketTypeDetails = apiService.getTicketTypeById(int, uid)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun createOrEditTicketType(ticketTypeDetails: TicketTypeDetails) {
        val apiService = APIService.getInstance()
        try {
            updateSuccessFull=false
            errorMessage=""
            apiService.createOrEditTicketType(ticketTypeDetails)
            updateSuccessFull=true
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun deleteTicketType(id:Int?) {
        val apiService = APIService.getInstance()
        try {
            deleteSuccessFull=false
            errorMessage=""
            apiService.deleteTicketType(id)
            deleteSuccessFull=true
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun getTrainerById(int:Int?, uid: String?){
        val apiService = APIService.getInstance()
        try {
            errorMessage=""
            trainerDetails = apiService.getTrainerById(int, uid)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun createOrEditTrainer(trainerDetails: TrainerDetails) {
        val apiService = APIService.getInstance()
        try {
            updateSuccessFull=false
            errorMessage=""
            apiService.createOrEditTrainer(trainerDetails)
            updateSuccessFull=true
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun deleteTrainer(id:Int?) {
        val apiService = APIService.getInstance()
        try {
            deleteSuccessFull=false
            errorMessage=""
            apiService.deleteTrainer(id)
            deleteSuccessFull=true
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }
}
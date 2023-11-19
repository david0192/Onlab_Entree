package com.entree.entreeapp.models

import androidx.compose.runtime.MutableState
import java.util.*

data class User(
    var email: String?,
    var roleId: Int,
    var uid: String?
)

data class SportFacility(
    var id:Int,
    var name: String,
    var site:String
)

data class SportFacilityDetails(
    var id:Int,
    var name: String,
    var site:String,
    var ticketTypes:List<TicketType>,
    var trainers: List<Trainer>
)

data class TicketType(
    var id:Int,
    var name: String,
    var price:Int
)

data class Ticket(
    var typeName: String?
)

data class Trainer(
    var id:Int,
    var name: String
)

data class SportFacilityStatisticsQuery(
    var uid:String?,
    var beginTime: Date?,
    var endTime: Date?
)

data class SportFacilityStatisticsResult(
    var revenue:Int,
    var ticketTypeBuyNumbers:Map<String, Int>,
)

data class TicketTypeDetails(
    var id:Int,
    var name:String,
    var price:Int,
    var categoryId: Int,
    var maxUsages: Int?,
    var validityDays: Int?,
    var sportFacilityId:Int,
    var categoryValues:Map<Int, String>
)

data class TrainerDetails(
    var id:Int,
    var name:String,
    var sportFacilityId:Int,
    var introduction: String?
)
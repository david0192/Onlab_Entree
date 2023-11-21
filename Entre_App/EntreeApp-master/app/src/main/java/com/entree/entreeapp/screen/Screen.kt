package com.entree.entreeapp.screen

const val SPORT_DETAIL_ARGUMENT_KEY="id"
const val ADMIN_TICKETTYPE_DETAIL_ARGUMENT_KEY="id"
const val ADMIN_TICKETTYPE_DETAIL_NAVIGATE_TYPE_ID="NavigateTypeId"
const val ADMIN_TRAINER_DETAIL_ARGUMENT_KEY="id"
const val ADMIN_TRAINER_DETAIL_NAVIGATE_TYPE_ID="NavigateTypeId"

sealed class Screen(val route:String){
    object Home:Screen(route="home_sportfacilities")
    object Profile:Screen(route="profile")
    object Detail:Screen(route="sportfacility_details/{$SPORT_DETAIL_ARGUMENT_KEY}")
    object Checkout:Screen(route="checkout/{ticketTypeId}/{amount}")
    object QrCode:Screen(route="qrcode")
    object TicketTypeDetail:Screen(route="ticketTypeDetail/{ticketTypeId}")
    object AdminSportFacility:Screen(route = "admin_sportfacility_details")
    object AdminStatistics:Screen(route = "admin_statistics")
    object AdminTicketTypeDetail:Screen(route="admin_ticketType_details/{$ADMIN_TICKETTYPE_DETAIL_ARGUMENT_KEY}/{$ADMIN_TICKETTYPE_DETAIL_NAVIGATE_TYPE_ID}")
    object AdminTrainerDetail:Screen(route="admin_trainer_details/{$ADMIN_TRAINER_DETAIL_ARGUMENT_KEY}/{$ADMIN_TRAINER_DETAIL_NAVIGATE_TYPE_ID}")
}

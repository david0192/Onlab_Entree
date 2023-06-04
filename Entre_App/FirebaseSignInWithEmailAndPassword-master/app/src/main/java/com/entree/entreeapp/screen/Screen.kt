package com.entree.entreeapp.screen

const val SPORT_DETAIL_ARGUMENT_KEY="id"

sealed class Screen(val route:String){
    object Home:Screen(route="home_sportfacilities")
    object Profile:Screen(route="profile")
    object Detail:Screen(route="sportfacility_details/{$SPORT_DETAIL_ARGUMENT_KEY}")
    object Checkout:Screen(route="checkout/{ticketTypeId}/{amount}")
    object QrCode:Screen(route="qrcode")




}

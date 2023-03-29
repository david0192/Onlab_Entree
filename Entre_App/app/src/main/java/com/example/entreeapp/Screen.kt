package com.example.entreeapp

const val SPORT_DETAIL_ARGUMENT_KEY="id"

sealed class Screen(val route:String){
    object Home:Screen(route="home_sportfacilities")
    object Profile:Screen(route="profile")
    object Detail:Screen(route="sportfacility_details/{$SPORT_DETAIL_ARGUMENT_KEY}")

}

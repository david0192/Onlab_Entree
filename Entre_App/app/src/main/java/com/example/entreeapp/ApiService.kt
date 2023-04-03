package com.example.entreeapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class User(
    var email: String,
    var role: String,
)

data class SportFacility(
    var id:Int,
    var name: String,
    var site:String
)

data class TicketType(
    var id:Int,
    var name: String,
    var price:Int
)

const val BASE_URL = "http://192.168.0.21:7111/api/"

interface APIService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("sportfacilities")
    suspend fun getSportFacilities(): List<SportFacility>

    @GET("sportfacilities/{id}/{catId}")
    suspend fun getSportFacilitiesByIdCatId(@Path("id") id: Int?, @Path("catId") catId:Int): List<TicketType>

    companion object {
        var apiService: APIService? = null
        fun getInstance(): APIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIService::class.java)
            }
            return apiService!!
        }
    }
}
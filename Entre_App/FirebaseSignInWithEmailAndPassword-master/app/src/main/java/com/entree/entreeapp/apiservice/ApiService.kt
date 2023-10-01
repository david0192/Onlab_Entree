package com.entree.entreeapp.apiservice

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


data class User(
    var email: String?,
    var role: String?,
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

data class Ticket(
    var typeName: String?
)

const val BASE_URL = "http://192.168.83.1:7111/api/"

interface APIService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("sportfacilities")
    suspend fun getSportFacilities(): List<SportFacility>

    @GET("sportfacilities/{id}/{catId}")
    suspend fun getSportFacilitiesByIdCatId(@Path("id") id: Int?, @Path("catId") catId:Int): List<TicketType>

    @GET("tickets/{email}")
    suspend fun getTicketsByEmail(@Path("email") email: String?): List<Ticket>

    @GET("users/role/{email}")
    suspend fun getRoleByEmail(@Path("email") email: String?): String?

    @POST("users")
    suspend fun AddGuestUser(@Body user: User)

    @POST("tickettypes/{ticketTypeId}/{email}")
    suspend fun AddTicketToUser(@Path("ticketTypeId") ticketTypeId:Int?, @Path("email")email: String?)

    companion object {
        var apiService: APIService? = null
        var gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        fun getInstance(): APIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(APIService::class.java)
            }
            return apiService!!
        }
    }
}
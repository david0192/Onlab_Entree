package com.entree.entreeapp.apiservice

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.Date
import java.util.Dictionary


data class User(
    var email: String?,
    var roleId: Int,
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
    var trainers:List<Trainer>
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
    var email:String?,
    var beginTime:Date?,
    var endTime:Date?
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
    var maxUsages: Int,
    var validityDays: Int,
    var categoryValues:Map<Int, String>
)

const val BASE_URL = "http://192.168.0.25:7111/api/"

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
    suspend fun getRoleByEmail(@Path("email") email: String?): Int

    @GET("sportfacility/{email}")
    suspend fun getSportFacilityByAdminEmail(@Path("email") email: String?): SportFacilityDetails

    @POST("users")
    suspend fun AddGuestUser(@Body user: User)

    @POST("tickettypes/{ticketTypeId}/{email}")
    suspend fun AddTicketToUser(@Path("ticketTypeId") ticketTypeId:Int?, @Path("email")email: String?)

    @POST("sportfacility")
    suspend fun updateSportFacility(@Body sportFacility: SportFacility)

    @POST("sportFacilityStatistics")
    suspend fun getSportFacilityStatistics(@Body sportFacilityStatisticsQuery:SportFacilityStatisticsQuery): SportFacilityStatisticsResult

    @GET("tickettype/{Id}")
    suspend fun getTicketTypeById(@Path("Id")id:Int?): TicketTypeDetails

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
package com.entree.entreeapp.apiservice
import com.entree.entreeapp.models.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.Date
import java.util.Dictionary

const val BASE_URL = "http://192.168.83.1:7111/api/"

interface APIService {
    @GET("sportfacilities")
    suspend fun getSportFacilities(): List<SportFacility>

    @GET("sportfacilities/{id}/{catId}")
    suspend fun getSportFacilitiesByIdCatId(@Path("id") id: Int?, @Path("catId") catId:Int): List<TicketType>

    @GET("tickets/{uid}")
    suspend fun getTicketsByUid(@Path("uid") uid: String?): List<Ticket>

    @GET("users/role/{email}")
    suspend fun getRoleIdByEmail(@Path("email") email: String?): Int

    @GET("sportfacility/{uid}")
    suspend fun getSportFacilityByAdminUid(@Path("uid") uid: String?): SportFacilityDetails

    @POST("users")
    suspend fun addGuestUser(@Body user: User)

    @POST("tickettypes/{ticketTypeId}/{uid}")
    suspend fun addTicketToUser(@Path("ticketTypeId") ticketTypeId:Int?, @Path("uid")uid: String?)

    @POST("sportfacility")
    suspend fun updateSportFacility(@Body sportFacility: SportFacility)

    @POST("ticketType")
    suspend fun createOrEditTicketType(@Body ticketTypeDetails: TicketTypeDetails)

    @GET("sportFacilityStatistics")
    suspend fun getSportFacilityStatistics(@Query("uid") uid: String?, @Query("beginTime") beginTime: String?, @Query("endTime") endTime: String?): SportFacilityStatisticsResult

    @GET("tickettype/{Id}/{uid}")
    suspend fun getTicketTypeById(@Path("Id")id:Int?, @Path("uid")uid: String?): TicketTypeDetails

    @DELETE("ticketType/{id}")
    suspend fun DeleteTicketType(@Path("id")id:Int?)

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
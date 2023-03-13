package com.example.apiexample.api

import com.example.entreeapp.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

public interface UserApi {
    @Headers(
        "Accept: application/json"
    )
    @GET("users")
    abstract fun getUser(): Call<User?>?
}
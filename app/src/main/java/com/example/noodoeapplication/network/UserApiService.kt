package com.example.noodoeapplication.network

import com.example.noodoeapplication.data.UserInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val LOGIN_URL = "https://watch-master-staging.herokuapp.com/api/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(LOGIN_URL)
    .build()


interface UserApiService {


    @GET("login/")
    suspend fun getUserInfo(
        @HeaderMap headers: Map<String,String>,
        @Query("username") username: String,
        @Query("password")password: String
    ): Response<UserInfo>


    @PUT("users/{id}")
    suspend fun updateUserInfo(
        @HeaderMap headers: Map<String,String>,
        @Path("id") objectId: String,
        @Body timezone: RequestBody
    ):Response<Any>

}

object UserApi{
    val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}
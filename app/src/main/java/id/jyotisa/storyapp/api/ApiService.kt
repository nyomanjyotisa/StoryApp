package id.jyotisa.storyapp.api

import id.jyotisa.storyapp.model.LoginResponse
import id.jyotisa.storyapp.model.RegisResponse
import id.jyotisa.storyapp.model.Story
import id.jyotisa.storyapp.model.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}
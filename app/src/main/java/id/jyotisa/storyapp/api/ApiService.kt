package id.jyotisa.storyapp.api

import id.jyotisa.storyapp.helper.Network.TOKEN
import id.jyotisa.storyapp.model.*
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


    @GET("stories")
    @Headers("Authorization: $TOKEN")
    fun getStories(@Query("page") page: Int): Call<StoryResponse>
}
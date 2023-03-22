package com.example.myapplication.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

// Use guide found on https://square.github.io/retrofit/

private const val BASE_URL =
    "http://192.168.50.219:3000"

private val mMoshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val mRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(mMoshi))
    .baseUrl(BASE_URL)
    .build()

interface LilyApiService {
    @GET("posts")
    @Headers("Accept: application/json")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    @Headers("Accept: application/json")
    suspend fun getPost(@Path("id") id: Int): Post

    @POST("posts")
    @Headers("Accept: application/json")
    suspend fun createPost(@Body post: Post): Post
}

object LilyApi {
    val retrofitService: LilyApiService by lazy { mRetrofit.create(LilyApiService::class.java) }
}
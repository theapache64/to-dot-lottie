package com.github.theapache64.todotlottie

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST


interface ConvertApi {
    @POST("todotlottie")
    suspend fun toDotLottie(
        @Body convertRequest: ConvertRequest
    ): ConvertResponse
}

@Serializable
data class ConvertResponse(
    @SerialName("converted_file_size")
    val convertedFileSize: String,
    @SerialName("file")
    val `file`: String,
    @SerialName("source_file_size")
    val sourceFileSize: String
)

@Serializable
data class ConvertRequest(
    @SerialName("url")
    val url: String
)

val convertApi: ConvertApi = Retrofit.Builder()
    .baseUrl("https://api.dotlottie.io/")
    .addConverterFactory(json.asConverterFactory(MediaType.parse("application/json")!!))
    .build()
    .create(ConvertApi::class.java)
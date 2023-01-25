package com.github.theapache64.todotlottie

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    prettyPrint = true
    prettyPrintIndent = "  "
}

@Serializable
data class UploadRequest(
    @SerialName("payload")
    val payload: String
)

@Serializable
data class UploadResponse(
    @SerialName("payload")
    val payload: Payload
) {
    @Serializable
    data class Payload(
        @SerialName("data_file")
        val dataFile: String,
        @SerialName("filetype")
        val filetype: String,
        @SerialName("hash")
        val hash: String,
        @SerialName("id")
        val id: Int
    )
}

interface UploadApi {
    @POST("temp-file-upload")
    suspend fun tempFileUpload(
        @Body request: UploadRequest
    ): UploadResponse
}

val uploadApi: UploadApi = Retrofit.Builder()
    .baseUrl("https://api.lottiefiles.com/v2/")
    .addConverterFactory(json.asConverterFactory(MediaType.parse("application/json")!!))
    .build()
    .create(UploadApi::class.java)


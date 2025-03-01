package dev.alfin.crackmeandroid

import com.google.gson.annotations.SerializedName
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

object HttpClient {
    private const val HOST_NAME = "meowfacts.herokuapp.com"
    private const val BASE_URL = "https://$HOST_NAME"
    private const val API_TIMEOUT = 60L

    private val instance: Retrofit by lazy {
        val certificatePinner = CertificatePinner.Builder()
            .add(HOST_NAME, "sha256/E6N34jNy4y5V+XdkUDda5lAab65zxERycdxZ9cosxSI=")
            .build()

        val clientBuilder: OkHttpClient.Builder =
            OkHttpClient()
                .newBuilder()
                .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
    }

    val catService: CatService by lazy {
        instance.create(CatService::class.java)
    }
}

interface CatService {
    @GET("/")
    fun getCatFacts(): Call<CatResponse>
}

data class CatResponse(@SerializedName("data") val data: List<String>)


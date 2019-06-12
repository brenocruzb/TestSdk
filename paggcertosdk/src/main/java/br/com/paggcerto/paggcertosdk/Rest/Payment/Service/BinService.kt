package br.com.paggcerto.paggcertosdk.Rest.Payment.Service

import retrofit2.Call
import retrofit2.http.GET

internal interface BinService {

    @GET("bins")
    fun getBins(): Call<String>
}
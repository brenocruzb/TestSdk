package br.com.paggcerto.paggcertosdk.Rest.Account

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface AccountService {
    @GET("whoami")
    fun identify(): Call<String>

    @POST("{applicationId}/signin")
    fun login(@Path("applicationId") applicationId: String, @Body loginObject: RequestBody): Call<String>
}
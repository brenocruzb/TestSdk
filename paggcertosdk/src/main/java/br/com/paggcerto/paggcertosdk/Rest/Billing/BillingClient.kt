package br.com.paggcerto.paggcertosdk.Rest.Billing

import br.com.paggcerto.paggcertosdk.Model.Pagg_Token
import br.com.paggcerto.paggcertosdk.Util.Util
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

internal object BillingClient {
    private var retrofit: Retrofit? = null

    private const val timeOut = 30

    fun getClient(token: Pagg_Token): Retrofit?{
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain: Interceptor.Chain ->
                    val newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token.token)
                            .build()

                    chain.proceed(newRequest)
                }
                .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
                .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit
                .Builder()
                .baseUrl(Util.BILLING_API_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

        return retrofit
    }
}
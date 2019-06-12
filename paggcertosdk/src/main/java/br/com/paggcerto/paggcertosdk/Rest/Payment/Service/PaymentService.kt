package br.com.paggcerto.paggcertosdk.Rest.Payment.Service

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

internal interface PaymentService {
    @GET("plans/transfer-days")
    fun getTransferDays(): Call<String>

    @GET("all")
    fun getHistoryPayments(@QueryMap filter: Map<String, String>): Call<String>

    @GET("find/{paymentId}")
    fun getPaymentDetail(@Path("paymentId") paymentId: String): Call<String>

    @POST("card-transactions/send-receipt/{nsu}")
    fun sendReceipt(@Path("nsu") nsu: String, @Body receiptObject: RequestBody): Call<String>

    @GET("bank-slips/pdf/{paymentId}")
    fun getBankSlipsPDF(@Path("paymentId") paymentId: String): Call<ResponseBody>

    @POST("card-transactions/cancel/{nsu}")
    fun cancelCardTransaction(@Path("nsu") nsu: String): Call<String>

    @POST("cancel/{paymentId}")
    fun cancelPayment(@Path("paymentId") paymentId: String): Call<String>

    @POST("pay/createCard")
    fun payWithCard(@Body payObject: RequestBody): Call<String>

    @POST("pay/bank-slips")
    fun payBankSlips(@Body payObject: RequestBody): Call<String>

    @POST("finalize/{paymentId}")
    fun finishPayment(@Path("paymentId") paymentId: String, @Body jsonObject: RequestBody): Call<String>

    @POST("bank-slips/cancel/{number}")
    fun cancelBankSlip(@Path("number") number: String, @Body jsonObject: RequestBody): Call<String>

    @POST("bank-slips/replace/{number}")
    fun replaceBankslips(@Path("number") bankslipsNumber: String, @Body jsonObject: RequestBody): Call<String>

    @GET("anticipations")
    fun anticipationHistory(@QueryMap filter: Map<String, String>): Call<String>

    @GET("anticipations/card-transactions-available")
    fun anticipatedTransactions(@QueryMap filter: Map<String, String>): Call<String>

    @POST("anticipations/new-request/selected-card-transactions")
    fun newAnticipationRequest(@Body transactionsObject: RequestBody): Call<String>

    @GET("anticipations/{anticipationId}/card-transactions")
    fun anticipationTransactions(@Path("anticipationId") anticipationId: String, @QueryMap filter: Map<String, String>): Call<String>

    @GET("transfers")
    fun getHistoryTransfers(@QueryMap filter: Map<String, String>): Call<String>

    @GET("transfers/scheduled")
    fun getFutureTransfers(@QueryMap filter: Map<String, String>): Call<String>

    @POST("pay/simulate")
    fun simulatePay(@Body payObject: RequestBody): Call<String>

    @GET("transfers/balance")
    fun balance(): Call<String>

    @POST("cards")
    fun createCard(@Body cardsObject: RequestBody): Call<String>

    @GET("cards")
    fun cards(@QueryMap filter: Map<String, String>): Call<String>

    @GET("cards/{id}")
    fun findCard(@Path("id") idCard: String): Call<String>

    @DELETE("cards/{id}")
    fun removeCard(@Path("id") idCard: String): Call<String>

    @GET("transfers/{id}")
    fun getTransfersDetail(@Path("id") id: String): Call<String>

    @POST("splitters")
    fun registerSplitter(@Body splitObject: RequestBody): Call<String>

    @PUT("splitters/{id}")
    fun updateSplitter(@Path("id") id:String, @Body splitObject: RequestBody): Call<String>

    @GET("splitters")
    fun listSplitter(@QueryMap filter: Map<String, String>): Call<String>

    @GET("splitters/find/{id}")
    fun getSplitter(@Path("id") id: String): Call<String>

    @GET("transfers/splitters")
    fun getTransferSplitters(@QueryMap filter: Map<String, String>): Call<String>

}
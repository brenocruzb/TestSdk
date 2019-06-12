package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_BillingsResponse: Serializable {
    @SerializedName("total")
    @Expose
    var total: Pagg_TotalBilling? = null

    @SerializedName("billings")
    @Expose
    var billings: List<Pagg_BillingResponse>? = null

    @SerializedName("count")
    @Expose
    var count: Int = 0
}
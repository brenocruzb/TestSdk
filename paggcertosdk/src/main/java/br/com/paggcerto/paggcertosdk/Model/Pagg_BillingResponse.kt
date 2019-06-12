package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_BillingResponse: Pagg_BillingRequest(), Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("created")
    @Expose
    var created: String? = null

    @SerializedName("updated")
    @Expose
    var updated: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("totalDiscountCard")
    @Expose
    var totalDiscountCard: Double? = null

    @SerializedName("totalDiscountBankSlip")
    @Expose
    var totalDiscountBankSlip: Double? = null

    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("smsSent")
    @Expose
    var smsSent: Int? = null

    @SerializedName("billingEvents")
    @Expose
    var billingEvents: List<Pagg_BillingEvents>? = null
}
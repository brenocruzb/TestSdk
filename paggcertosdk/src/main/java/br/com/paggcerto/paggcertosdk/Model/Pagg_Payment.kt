package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

class Pagg_Payment: Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("sellingKey")
    @Expose
    var sellingKey: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("canceledAt")
    @Expose
    var canceledAt: String? = null

    @SerializedName("completedAt")
    @Expose
    var completedAt: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("amountPaid")
    @Expose
    var amountPaid: Double? = null

    @SerializedName("cancelable")
    @Expose
    var cancelable: Boolean? = null

    @SerializedName("additionalInformation")
    @Expose
    var additionalInformation: String? = null

    @SerializedName("cardTransactions")
    @Expose
    var cardTransactions: List<Pagg_CardTransaction> = ArrayList()

    @SerializedName("bankSlips")
    @Expose
    var bankSlips: List<Pagg_BankSlips> = ArrayList()

    @SerializedName("note")
    @Expose
    var note: String? = null
}
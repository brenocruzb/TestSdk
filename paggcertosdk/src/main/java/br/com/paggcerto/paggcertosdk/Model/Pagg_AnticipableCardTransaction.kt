package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_AnticipableCardTransaction: Serializable {
    @SerializedName("nsu")
    @Expose
    var nsu: Long? = null

    @SerializedName("cardBrand")
    @Expose
    var cardBrand: String? = null

    @SerializedName("cardFinal")
    @Expose
    var cardFinal: String? = null

    @SerializedName("online")
    @Expose
    var online: Boolean? = null

    @SerializedName("amountPaid")
    @Expose
    var amountPaid: Double? = null

    @SerializedName("amountAnticipated")
    @Expose
    var amountAnticipated: Double? = null

    @SerializedName("installments")
    @Expose
    var installments: Int? = null

    @SerializedName("installmentValue")
    @Expose
    var installmentValue: Double? = null

    @SerializedName("paidAt")
    @Expose
    var paidAt: String? = null
}
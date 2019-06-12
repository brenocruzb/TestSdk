package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_CardTransaction: Serializable {
    @SerializedName("nsu")
    @Expose
    var nsu: Long? = null

    @SerializedName("cardBrand")
    @Expose
    var cardBrand: String? = null

    @SerializedName("cardFinal")
    @Expose
    var cardFinal: String? = null

    @SerializedName("credit")
    @Expose
    var credit: Boolean? = null

    @SerializedName("online")
    @Expose
    var online: Boolean? = null

    @SerializedName("amountPaid")
    @Expose
    var amountPaid: Double? = null

    @SerializedName("installments")
    @Expose
    var installments: Int? = null

    @SerializedName("installmentValue")
    @Expose
    var installmentValue: Double? = null

    @SerializedName("canceledAt")
    @Expose
    var canceledAt: String? = null

    @SerializedName("contestedOn")
    @Expose
    var contestedOn: String? = null

    @SerializedName("reprovedAt")
    @Expose
    var reprovedAt: String? = null

    @SerializedName("paidAt")
    @Expose
    var paidAt: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("error")
    @Expose
    var error: String? = null

}
package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Pagg_Transfer: Serializable {
    @SerializedName("id")
    @Expose
    var id: Long? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("scheduledFor")
    @Expose
    var scheduledFor: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("bankTransferFee")
    @Expose
    var bankTransferFee: Double? = null

    @SerializedName("bankAccount")
    @Expose
    var bankAccount: Pagg_BankAccount? = null
}
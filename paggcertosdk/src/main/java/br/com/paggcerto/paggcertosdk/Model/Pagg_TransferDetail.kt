package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_TransferDetail: Serializable {
    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("otherAmount")
    @Expose
    var otherAmount: Double? = null

    @SerializedName("bankSlipAmount")
    @Expose
    var bankSlipAmount: Double? = null

    @SerializedName("cardAmount")
    @Expose
    var cardAmount: Double? = null

    @SerializedName("creditCardAmount")
    @Expose
    var creditCardAmount: Double? = null

    @SerializedName("debitCardAmount")
    @Expose
    var debitCardAmount: Double? = null

    @SerializedName("scheduledFor")
    @Expose
    var scheduledFor: String? = null

    @SerializedName("bankTransferFee")
    @Expose
    var bankTransferFee: Double? = null

    @SerializedName("bankAccount")
    @Expose
    var bankAccount: Pagg_BankAccount? = null

}
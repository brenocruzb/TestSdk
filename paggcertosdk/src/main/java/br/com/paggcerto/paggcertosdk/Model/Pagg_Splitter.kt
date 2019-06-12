package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Splitter: Serializable {

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("salesCommission")
    @Expose
    var salesCommission: Double? = null

    @SerializedName("transferDays")
    @Expose
    var transferDays: Int? = null

    @SerializedName("anticipatedTransfer")
    @Expose
    var anticipatedTransfer: Boolean? = null

    @SerializedName("bankAccount")
    @Expose
    var bankAccount: Pagg_BankAccount? = null

    @SerializedName("paysFee")
    @Expose
    var paysFee: Boolean? = null

    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("address")
    @Expose
    var address: Pagg_Address? = null
}
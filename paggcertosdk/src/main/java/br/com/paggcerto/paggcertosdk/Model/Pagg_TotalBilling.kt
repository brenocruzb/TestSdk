package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_TotalBilling: Serializable {
    @SerializedName("amount")
    @Expose
    var amount: Double = 0.0

    @SerializedName("pending")
    @Expose
    var pending: Double = 0.0

    @SerializedName("paid")
    @Expose
    var paid: Double = 0.0

    @SerializedName("canceled")
    @Expose
    var canceled: Double = 0.0
}
package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_RequestCardTransaction: Serializable {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("requestedInstallments")
    @Expose
    var requestedInstallments: Int = 0

    @SerializedName("transferredInstallments")
    @Expose
    var transferredInstallments: Int = 0

    @SerializedName("approvedInstallments")
    @Expose
    var approvedInstallments: Int = 0

    @SerializedName("requestedAmount")
    @Expose
    var requestedAmount: Double = 0.toDouble()

    @SerializedName("approvedAmount")
    @Expose
    var approvedAmount: Double = 0.toDouble()

    @SerializedName("note")
    @Expose
    var note: String? = null

    @SerializedName("cardTransaction")
    @Expose
    var cardTransaction: Pagg_CardTransaction? = null
}
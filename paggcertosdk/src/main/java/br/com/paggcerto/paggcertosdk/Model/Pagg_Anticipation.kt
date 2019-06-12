package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Anticipation: Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("solicitedAt")
    @Expose
    var solicitedAt: String? = null

    @SerializedName("completedAt")
    @Expose
    var completedAt: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("amountRequested")
    @Expose
    var amountRequested: Double? = null

    @SerializedName("amountApproved")
    @Expose
    var amountApproved: Double? = null
}
package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_NextTransfer: Serializable {
    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("scheduledFor")
    @Expose
    var scheduledFor: String? = null
}
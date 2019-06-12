package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_AnticipatedTransfer: Serializable {
    @SerializedName("amountAnticipated")
    @Expose
    var amountAnticipated: Double? = null

    @SerializedName("amountToReceive")
    @Expose
    var amountToReceive: Double? = null
}
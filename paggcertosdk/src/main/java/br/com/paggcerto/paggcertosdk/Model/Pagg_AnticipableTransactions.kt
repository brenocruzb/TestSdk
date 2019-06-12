package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_AnticipableTransactions: Serializable {

    @SerializedName("requestedCardTransactions")
    @Expose
    var transactions: List<Pagg_RequestCardTransaction>? = null

    @SerializedName("count")
    @Expose
    var count: Int? = null
}
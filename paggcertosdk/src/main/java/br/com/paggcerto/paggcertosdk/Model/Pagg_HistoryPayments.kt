package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_HistoryPayments: Serializable {
    @SerializedName("total")
    @Expose
    var total: Pagg_Total? = null

    @SerializedName("payments")
    @Expose
    var payments: List<Pagg_HistoryPayment>? = null

    @SerializedName("count")
    @Expose
    var count: Long? = null
}
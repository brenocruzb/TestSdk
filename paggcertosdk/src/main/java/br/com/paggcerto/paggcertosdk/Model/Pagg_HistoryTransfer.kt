package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_HistoryTransfer: Serializable {
    @SerializedName("nextTransfer")
    @Expose
    var nextTransfer: Pagg_NextTransfer? = null

    @SerializedName("transfers")
    @Expose
    var transfers: List<Pagg_Transfer>? = null

    @SerializedName("count")
    @Expose
    var count: Long? = null
}
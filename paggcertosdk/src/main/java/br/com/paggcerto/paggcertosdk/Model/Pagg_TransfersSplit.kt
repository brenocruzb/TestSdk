package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_TransfersSplit: Serializable {
    @SerializedName("transfers")
    @Expose
    var transfers: List<Pagg_TransferSplit>? = null

    @SerializedName("count")
    @Expose
    var count: Int? = null
}
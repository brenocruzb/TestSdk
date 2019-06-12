package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_TransferDays: Serializable {
    @SerializedName("transferDays")
    @Expose
    var transferDays: List<Long>? = null

    @SerializedName("count")
    @Expose
    var count: Long? = null
}
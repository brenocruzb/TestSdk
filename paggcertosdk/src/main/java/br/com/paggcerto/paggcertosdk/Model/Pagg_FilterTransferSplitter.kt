package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_FilterTransferSplitter: Pagg_FilterTransfer(), Serializable {
    @SerializedName("splittersId")
    @Expose
    var splittersId: List<String>? = null

    @SerializedName("status")
    @Expose
    var status: String? = null
}
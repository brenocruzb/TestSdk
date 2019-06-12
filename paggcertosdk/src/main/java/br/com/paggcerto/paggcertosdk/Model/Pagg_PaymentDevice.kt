package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_PaymentDevice : Serializable {
    @SerializedName("serialNumber")
    @Expose
    var serialNumber: String? = null

    @SerializedName("model")
    @Expose
    var model: String? = null
}
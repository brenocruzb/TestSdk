package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Simulation: Serializable {
    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("customerPaysFee")
    @Expose
    var customerPaysFee: Boolean? = null

    @SerializedName("installments")
    @Expose
    var installments: Int? = null

    @SerializedName("credit")
    @Expose
    var credit: Boolean? = null

    @SerializedName("pinpad")
    @Expose
    var pinpad: Boolean? = null

    @SerializedName("cardBrand")
    @Expose
    var cardBrand: String? = null
}
package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Bin : Serializable{
    @SerializedName("cardBrand")
    @Expose
    var cardBrand: String? = null

    @SerializedName("regex")
    @Expose
    var regex: String? = null

    @SerializedName("debit")
    @Expose
    var debit: Boolean? = null

    @SerializedName("maximumInstallment")
    @Expose
    var maximumInstallment: Int? = null

    @SerializedName("emvSupported")
    @Expose
    var emvSupported: Boolean = true
}
package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Payer: Serializable {

    @SerializedName("sellingKey")
    @Expose
    var sellingKey: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("taxDocument")
    @Expose
    var taxDocument: String? = null

    @SerializedName("address")
    @Expose
    var address: Pagg_Address? = null

    @SerializedName("bankSlips")
    @Expose
    var bankSlips: List<Pagg_BankSlips>? = null
}
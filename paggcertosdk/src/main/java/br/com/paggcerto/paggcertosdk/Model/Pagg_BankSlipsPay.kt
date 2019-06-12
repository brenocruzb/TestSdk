package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_BankSlipsPay: Serializable {
    @SerializedName("splitters")
    @Expose
    var splitters: List<Pagg_Splitter>? = null

    @SerializedName("note")
    @Expose
    var note: String? = null

    @SerializedName("payers")
    @Expose
    var payers: List<Pagg_Payer>? = null
}
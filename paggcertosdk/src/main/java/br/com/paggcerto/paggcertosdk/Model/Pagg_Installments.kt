package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Installments: Serializable {

    @SerializedName("dueDate")
    @Expose
    var dueDate: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Double? = null
}
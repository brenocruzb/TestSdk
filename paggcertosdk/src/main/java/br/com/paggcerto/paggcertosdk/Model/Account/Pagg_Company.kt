package br.com.paggcerto.paggcertosdk.Model.Account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Company: Serializable {
    @SerializedName("tradeName")
    @Expose
    var tradeName: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("taxDocument")
    @Expose
    var taxDocument: String? = null
}
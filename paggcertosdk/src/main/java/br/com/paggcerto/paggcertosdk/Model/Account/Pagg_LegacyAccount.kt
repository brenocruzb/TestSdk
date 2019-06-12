package br.com.paggcerto.paggcertosdk.Model.Account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_LegacyAccount: Serializable {
    @SerializedName("sellerId")
    @Expose
    var sellerId: Int? = null

    @SerializedName("planId")
    @Expose
    var planId: Int? = null

    @SerializedName("automaticallyAnticipation")
    @Expose
    var automaticallyAnticipation: Boolean? = null
}
package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_BillingEvents: Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("event")
    @Expose
    var event: String? = null

    @SerializedName("created")
    @Expose
    var created: String? = null
}
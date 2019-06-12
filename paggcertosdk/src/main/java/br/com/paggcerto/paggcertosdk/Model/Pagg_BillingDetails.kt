package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_BillingDetails : Serializable{
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("sku")
    @Expose
    var sku: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null

    @SerializedName("price")
    @Expose
    var price: Double? = null
}
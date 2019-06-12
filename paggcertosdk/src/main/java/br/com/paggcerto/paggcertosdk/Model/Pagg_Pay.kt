package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Pay: Serializable {
    @SerializedName("sellingKey")
    @Expose
    var sellingKey: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("createCard")
    @Expose
    var cards: List<Pagg_Card>? = null

    @SerializedName("paymentDevice")
    @Expose
    var paymentDevice: Pagg_PaymentDevice? = null

    @SerializedName("mobileDevice")
    @Expose
    var mobileDevice: Pagg_MobileDevice? = null

    @SerializedName("geolocation")
    @Expose
    var geolocation: Pagg_Geolocation? = null
}
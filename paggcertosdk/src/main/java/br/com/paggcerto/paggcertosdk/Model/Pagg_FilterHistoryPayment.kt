package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_FilterHistoryPayment: Serializable {
    @SerializedName("nsu")
    @Expose
    var nsu: Long? = null

    @SerializedName("cardBrand")
    @Expose
    var cardBrand: String? = null

    @SerializedName("cardFinal")
    @Expose
    var cardFinal: String? = null

    @SerializedName("bankslipId")
    @Expose
    var bankslipId: Long? = null

    @SerializedName("note")
    @Expose
    var note: String? = null

    @SerializedName("startDate")
    @Expose
    var startDate: String? = null

    @SerializedName("endDate")
    @Expose
    var endDate: String? = null

    @SerializedName("paymentMethod")
    @Expose
    var paymentMethod: List<String>? = null

    @SerializedName("status")
    @Expose
    var status: List<String>? = null

    @SerializedName("payerName")
    @Expose
    var payerName: String? = null

    @SerializedName("payerTaxDocument")
    @Expose
    var payerTaxDocument: String? = null

    @SerializedName("index")
    @Expose
    var index: Long? = null

    @SerializedName("length")
    @Expose
    var length: Long? = null
}
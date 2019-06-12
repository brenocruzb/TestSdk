package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_HistoryPayment: Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("method")
    @Expose
    var method: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("completedAt")
    @Expose
    var completedAt: String? = null

    @SerializedName("canceledAt")
    @Expose
    var canceledAt: String? = null

    @SerializedName("firstDelayDate")
    @Expose
    var firstDelayDate: String? = null

    @SerializedName("nextDueDate")
    @Expose
    var nextDueDate: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("amountPaid")
    @Expose
    var amountPaid: Double? = null

    @SerializedName("cancelable")
    @Expose
    var cancelable: Boolean? = null

    @SerializedName("cardBrand")
    @Expose
    var cardBrand: String? = null

    @SerializedName("installments")
    @Expose
    var installments: Int? = null

    @SerializedName("installmentValue")
    @Expose
    var installmentValue: Double? = null

    @SerializedName("installmentNumber")
    @Expose
    var installmentNumber: Int? = null

    @SerializedName("payerName")
    @Expose
    var payerName: String? = null

    @SerializedName("payerTaxDocument")
    @Expose
    var payerTaxDocument: String? = null
}
package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_BankSlips: Serializable {

    @SerializedName("number")
    @Expose
    var number: Long? = null

    @SerializedName("payerName")
    @Expose
    var payerName: String? = null

    @SerializedName("payerTaxDocument")
    @Expose
    var payerTaxDocument: String? = null

    @SerializedName("dueDate")
    @Expose
    var dueDate: String? = null

    @SerializedName("discountLimitDate")
    @Expose
    var discountLimitDate: String? = null

    @SerializedName("discount")
    @Expose
    var discount: Double? = null

    @SerializedName("fines")
    @Expose
    var fines: Double? = null

    @SerializedName("interest")
    @Expose
    var interest: Double? = null

    @SerializedName("canceledAt")
    @Expose
    var canceledAt: String? = null

    @SerializedName("paidAt")
    @Expose
    var paidAt: String? = null

    @SerializedName("autoCanceledAt")
    @Expose
    var autoCanceledAt: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Double? = null

    @SerializedName("amountPaid")
    @Expose
    var amountPaid: Double? = null

    @SerializedName("installmentNumber")
    @Expose
    var installmentNumber: Int? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("barcode")
    @Expose
    var barcode: String? = null

    @SerializedName("instructions")
    @Expose
    var instructions: String? = null

    @SerializedName("sellingKey")
    @Expose
    var sellingKey: String? = null

    @SerializedName("discountDays")
    @Expose
    var discountDays: Int? = null

    @SerializedName("acceptedUntil")
    @Expose
    var acceptedUntil: Int? = null
}
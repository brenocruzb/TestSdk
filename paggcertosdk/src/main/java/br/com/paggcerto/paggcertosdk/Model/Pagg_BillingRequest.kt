package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Pagg_BillingRequest: Serializable {

        @SerializedName("fullName")
        @Expose
        var fullName: String? = null

        @SerializedName("document")
        @Expose
        var document: String? = null

        @SerializedName("phoneNumber")
        @Expose
        var phoneNumber: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("payment")
        @Expose
        var payment: String? = null

        @SerializedName("fixedInstallments")
        @Expose
        var fixedInstallments: Boolean? = null

        @SerializedName("maximumInstallments")
        @Expose
        var maximumInstallments: Int? = null

        @SerializedName("discountCard")
        @Expose
        var discountCard: Double? = null

        @SerializedName("discountBankSlip")
        @Expose
        var discountBankSlip: Double? = null

        @SerializedName("dueDate")
        @Expose
        var dueDate: String? = null

        @SerializedName("billingDetails")
        @Expose
        var billingDetails: List<Pagg_BillingDetails>? = null
}

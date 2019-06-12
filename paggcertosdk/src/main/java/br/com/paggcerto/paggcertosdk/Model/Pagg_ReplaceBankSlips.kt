package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_ReplaceBankSlips: Serializable {
    @SerializedName("discount")
    @Expose
    var discount: Double? = null

    @SerializedName("fines")
    @Expose
    var fines: Double? = null

    @SerializedName("interest")
    @Expose
    var interest: Double? = null

    @SerializedName("discountDays")
    @Expose
    var discountDays: Int? = null

    @SerializedName("acceptedUntil")
    @Expose
    var acceptedUntil: Int? = null

    @SerializedName("dueDate")
    @Expose
    var dueDate: String? = null

    @SerializedName("instructions")
    @Expose
    var instructions: String? = null
}
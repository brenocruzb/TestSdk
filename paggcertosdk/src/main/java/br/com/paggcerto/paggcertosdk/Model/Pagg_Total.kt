package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Total : Serializable {

    @SerializedName("pendingQuantity")
    @Expose
    var pendingQuantity: Long? = null

    @SerializedName("pendingAmount")
    @Expose
    var pendingAmount: Double? = null

    @SerializedName("paidQuantity")
    @Expose
    var paidQuantity: Long? = null

    @SerializedName("paidAmount")
    @Expose
    var paidAmount: Double? = null

    @SerializedName("contestedQuantity")
    @Expose
    var contestedQuantity: Long? = null

    @SerializedName("contestedAmount")
    @Expose
    var contestedAmount: Double? = null

    @SerializedName("delayedQuantity")
    @Expose
    var delayedQuantity: Long? = null

    @SerializedName("delayedAmount")
    @Expose
    var delayedAmount: Double? = null

    @SerializedName("canceledQuantity")
    @Expose
    var canceledQuantity: Long? = null

    @SerializedName("canceledAmount")
    @Expose
    var canceledAmount: Double? = null

    @SerializedName("registrationErrorQuantity")
    @Expose
    var registrationErrorQuantity: Long? = null

    @SerializedName("registrationErrorAmount")
    @Expose
    var registrationErrorAmount: Double? = null
}
package br.com.paggcerto.paggcertosdk.Model.Account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Account: Serializable {
    @SerializedName("active")
    @Expose
    var active: Boolean? = null

    @SerializedName("saleOnline")
    @Expose
    var saleOnline: Boolean? = null

    @SerializedName("approved")
    @Expose
    var approved: Boolean? = null

    @SerializedName("reproved")
    @Expose
    var reproved: Boolean? = null

    @SerializedName("freeTrial")
    @Expose
    var freeTrial: Boolean? = null

    @SerializedName("anticipatedTransfer")
    @Expose
    var anticipatedTransfer: Boolean? = null

    @SerializedName("oldAnticipationPlan")
    @Expose
    var oldAnticipationPlan: Boolean? = null

    @SerializedName("isOriginPartner")
    @Expose
    var isOriginPartner: Boolean? = null

    @SerializedName("balanceBlocked")
    @Expose
    var balanceBlocked: Boolean? = null

    @SerializedName("vanBanese")
    @Expose
    var vanBanese: Int? = null

    @SerializedName("comercialName")
    @Expose
    var comercialName: String? = null

    @SerializedName("softDescriptor")
    @Expose
    var softDescriptor: String? = null

    @SerializedName("legacy")
    @Expose
    var legacy: Pagg_LegacyAccount? = null

    @SerializedName("transferPlan")
    @Expose
    var transferPlan: Pagg_TransferPlan? = null

    @SerializedName("commercialName")
    @Expose
    var commercialName: String? = null
}
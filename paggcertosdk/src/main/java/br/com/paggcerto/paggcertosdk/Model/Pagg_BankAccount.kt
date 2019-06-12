package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_BankAccount: Serializable {
    @SerializedName("bankNumber")
    @Expose
    var bankNumber: String? = null

    @SerializedName("bankName")
    @Expose
    var bankName: String? = null

    @SerializedName("accountNumber")
    @Expose
    var accountNumber: String? = null

    @SerializedName("bankBranchNumber")
    @Expose
    var bankBranchNumber: String? = null

    @SerializedName("variation")
    @Expose
    var variation: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("holderName")
    @Expose
    var holderName: String? = null

    @SerializedName("taxDocument")
    @Expose
    var taxDocument: String? = null

    @SerializedName("isJuristic")
    @Expose
    var isJuristic: Boolean? = null
}
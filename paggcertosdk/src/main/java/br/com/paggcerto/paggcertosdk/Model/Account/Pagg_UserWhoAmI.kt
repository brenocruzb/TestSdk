package br.com.paggcerto.paggcertosdk.Model.Account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_UserWhoAmI: Serializable {
    @SerializedName("holder")
    @Expose
    var holder: Pagg_Holder? = null

    @SerializedName("account")
    @Expose
    var account: Pagg_Account? = null

    @SerializedName("user")
    @Expose
    var user: Pagg_User? = null

    @SerializedName("scope")
    @Expose
    var scope: String? = null

    @SerializedName("accessGranted")
    @Expose
    var accessGranted: Boolean? = null

    @SerializedName("accessedByHolder")
    @Expose
    var accessedByHolder: Boolean? = null

    @SerializedName("documentSent")
    @Expose
    var documentSent: Boolean? = null
}
package br.com.paggcerto.paggcertosdk.Model.Account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_TransferPlan: Serializable {
    @SerializedName("daysPinpad")
    @Expose
    var daysPinpad: Int? = null

    @SerializedName("daysOnline")
    @Expose
    var daysOnline: Int? = 32

    @SerializedName("anticipated")
    @Expose
    var anticipated: Boolean? = null

    @SerializedName("oldPlan")
    @Expose
    var oldPlan: Boolean? = false

    @SerializedName("migrate")
    @Expose
    var migrate: Boolean? = false
}
package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Balance: Serializable{
    @SerializedName("nextTransfer")
    @Expose
    var nextTransfer: Pagg_Transfer? = null

    @SerializedName("anticipatedTransfer")
    @Expose
    var anticipatedTransfer: Pagg_AnticipatedTransfer? = null

    @SerializedName("debitAmount")
    @Expose
    var debitAmount: Double? = null

    @SerializedName("creditAmount")
    @Expose
    var creditAmount: Double? = null
}
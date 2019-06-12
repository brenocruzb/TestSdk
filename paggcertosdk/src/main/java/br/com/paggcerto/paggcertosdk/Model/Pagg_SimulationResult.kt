package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_SimulationResult: Serializable {
    @SerializedName("amountReceived")
    var amountReceived: Double? = null

    @SerializedName("amountCharged")
    var amountCharged: Double? = null
}
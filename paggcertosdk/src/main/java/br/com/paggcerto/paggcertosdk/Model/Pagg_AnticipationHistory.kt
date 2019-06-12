package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_AnticipationHistory: Serializable {
    @SerializedName("inProgress")
    @Expose
    var inProgress: Pagg_Anticipation? = null

    @SerializedName("anticipations")
    @Expose
    var anticipations: List<Pagg_Anticipation>? = null

    @SerializedName("count")
    @Expose
    var count: Long? = null
}
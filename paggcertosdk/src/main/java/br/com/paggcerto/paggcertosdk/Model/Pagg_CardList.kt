package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_CardList: Serializable {
    @Expose
    @SerializedName("cards")
    var cards: List<Pagg_Card>? = null

    @Expose
    @SerializedName("count")
    var count: Int? = null
}
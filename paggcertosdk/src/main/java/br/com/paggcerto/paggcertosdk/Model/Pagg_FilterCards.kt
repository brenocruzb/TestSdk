package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_FilterCards: Serializable {
    @SerializedName("brands")
    @Expose
    var brands: List<String>? = null

    @SerializedName("ids")
    @Expose
    var ids: List<String>? = null

    @SerializedName("finals")
    @Expose
    var finals: List<String>? = null

    @SerializedName("index")
    @Expose
    var index: Int? = null

    @SerializedName("length")
    @Expose
    var length: Int? = null
}
package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_FilterSplitter: Serializable {

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("index")
    @Expose
    var index: Long? = null

    @SerializedName("length")
    @Expose
    var length: Long? = null
}
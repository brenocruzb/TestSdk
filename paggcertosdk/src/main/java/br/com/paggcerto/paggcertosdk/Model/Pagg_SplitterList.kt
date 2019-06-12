package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pagg_SplitterList {

    @SerializedName("splitters")
    @Expose
    var splitters: List<Pagg_Splitter>? = null

    @SerializedName("count")
    @Expose
    var count: Long? = null
}
package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_TransferSplit: Pagg_Transfer(), Serializable{

    @SerializedName("splitterId")
    @Expose
    var splitterId: String? = null
}
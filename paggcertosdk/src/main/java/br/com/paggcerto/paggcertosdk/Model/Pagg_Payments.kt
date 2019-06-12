package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

class Pagg_Payments: Serializable {
    @SerializedName("payments")
    @Expose
    var payments: List<Pagg_Payment> = ArrayList()
}
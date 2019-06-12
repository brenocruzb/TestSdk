package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_LoginForm: Serializable {
    @Expose
    @SerializedName("login")
    var login: String? = null

    @Expose
    @SerializedName("password")
    var password: String? = null
}
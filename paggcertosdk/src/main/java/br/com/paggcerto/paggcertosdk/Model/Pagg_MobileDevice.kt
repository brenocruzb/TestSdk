package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_MobileDevice: Serializable {

    @SerializedName("appVersion")
    @Expose
    var appVersion: String? = null

    @SerializedName("manufacturer")
    @Expose
    var manufacturer: String? = null

    @SerializedName("model")
    @Expose
    var model: String? = null

    @SerializedName("release")
    @Expose
    var release: String? = null

    @SerializedName("sdkVersion")
    @Expose
    var sdkVersion: Int = 0
}
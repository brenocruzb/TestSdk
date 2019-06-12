package br.com.paggcerto.paggcertosdk.Model.Account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_Holder: Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("birthDate")
    @Expose
    var birthDate: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("taxDocument")
    @Expose
    var taxDocument: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("company")
    @Expose
    var company: Pagg_Company? = null

}
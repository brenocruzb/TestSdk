package br.com.paggcerto.paggcertosdk.Model.Table

import br.com.paggcerto.paggcertosdk.Model.Table.Capk
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal class CapkTable {

    @SerializedName("Capk")
    @Expose
    var capk: List<Capk>? = null

}

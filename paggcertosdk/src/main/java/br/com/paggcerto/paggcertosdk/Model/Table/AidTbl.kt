package br.com.paggcerto.paggcertosdk.Model.Table

import br.com.paggcerto.paggcertosdk.Model.Table.Aid
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal class AidTbl {

    @SerializedName("Aid")
    @Expose
    var aid: List<Aid>? = null

}
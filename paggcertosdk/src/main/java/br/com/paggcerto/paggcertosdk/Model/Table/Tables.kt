package br.com.paggcerto.paggcertosdk.Model.Table

import br.com.paggcerto.paggcertosdk.Model.Table.AidTbl
import br.com.paggcerto.paggcertosdk.Model.Table.CapkTable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal class Tables {

    @SerializedName("AidTbl")
    @Expose
    var aidTbl: AidTbl? = null
    @SerializedName("CapkTable")
    @Expose
    var capkTable: CapkTable? = null

}
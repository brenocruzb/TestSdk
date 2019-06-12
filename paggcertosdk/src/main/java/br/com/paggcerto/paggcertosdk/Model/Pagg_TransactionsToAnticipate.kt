package br.com.paggcerto.paggcertosdk.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pagg_TransactionsToAnticipate: Serializable {
    @SerializedName("cardTransactions")
    @Expose
    var cardTransactions: List<Long>? = null
}
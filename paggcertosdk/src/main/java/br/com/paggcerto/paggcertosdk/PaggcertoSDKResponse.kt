package br.com.paggcerto.paggcertosdk

interface PaggcertoSDKResponse {
    fun onResult(result: Boolean, message: String)
}
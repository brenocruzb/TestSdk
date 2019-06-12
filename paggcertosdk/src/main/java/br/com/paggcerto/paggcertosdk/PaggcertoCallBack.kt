package br.com.paggcerto.paggcertosdk

interface PaggcertoCallBack<T> {
    fun onSuccess(obj: T)

    fun onError(code: Int, message: String)
}
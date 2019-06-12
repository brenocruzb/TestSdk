package br.com.paggcerto.paggcertosdk.Service.Interfaces

import br.com.paggcerto.paggcertosdk.Model.Pagg_Payment

private interface PaymentServiceCallBack {
    fun onSuccess(payment: Pagg_Payment)

    fun onError(message: String)
}
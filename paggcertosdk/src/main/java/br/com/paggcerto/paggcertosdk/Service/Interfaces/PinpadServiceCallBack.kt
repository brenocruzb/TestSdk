package br.com.paggcerto.paggcertosdk.Service.Interfaces

import br.com.paggcerto.paggcertosdk.Model.Pagg_Card

interface PinpadServiceCallBack {
    fun onSuccess(card: Pagg_Card, online: Boolean)

    fun onError(message: String)
}
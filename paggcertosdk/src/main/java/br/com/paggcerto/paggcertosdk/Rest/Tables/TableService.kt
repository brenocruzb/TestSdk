package br.com.paggcerto.paggcertosdk.Rest.Tables

import br.com.paggcerto.paggcertosdk.Model.Table.Tables
import retrofit2.Call
import retrofit2.http.GET

internal interface TableService {
    @GET("tabelas")
    fun listTables(): Call<Tables>
}
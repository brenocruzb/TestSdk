package br.com.paggcerto.paggcertosdk.Rest.Billing

import br.com.paggcerto.paggcertosdk.Model.Pagg_BillingRequest
import br.com.paggcerto.paggcertosdk.Model.Pagg_BillingResponse
import br.com.paggcerto.paggcertosdk.Model.Pagg_BillingsResponse
import br.com.paggcerto.paggcertosdk.Model.Pagg_FilterBilling
import br.com.paggcerto.paggcertosdk.PaggcertoCallBack
import br.com.paggcerto.paggcertosdk.PaggcertoSDK
import br.com.paggcerto.paggcertosdk.Util.JSONUtils
import br.com.paggcerto.paggcertosdk.Util.Util.printError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class BillingNetwork {
    private val appService = BillingClient.getClient(PaggcertoSDK.getInstance()?.token!!)

    private val connectionError = "Não foi possível conectar ao servidor Paggcerto. Tente novamente."

    private val error401 = "Usuário não autenticado (credenciais incorretas ou token inválido)"
    private val error403 = "Usuário autenticado, porém sem permissão (acesso negado)"
    private val unknownError = "Erro inesperado."

    private val gson = Gson()

    fun createBilling(billingRequest: Pagg_BillingRequest, callBack: PaggcertoCallBack<Pagg_BillingResponse>){
        val json = gson.toJson(billingRequest)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call = appService?.create(BillingService::class.java)?.createBilling(dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson<Pagg_BillingResponse>(response.body(), Pagg_BillingResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun listBillings(filterBilling: Pagg_FilterBilling, callBack: PaggcertoCallBack<Pagg_BillingsResponse>){
        var json = gson.toJson(filterBilling)
        json = JSONUtils.removeArrays(JSONObject(json)).toString()

        val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

        val call = appService?.create(BillingService::class.java)?.listBillings(retMap)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson<Pagg_BillingsResponse>(response.body(), Pagg_BillingsResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getBilling(idBilling: String, callBack: PaggcertoCallBack<Pagg_BillingResponse>){
        val call = appService?.create(BillingService::class.java)?.getBilling(idBilling)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson<Pagg_BillingResponse>(response.body(), Pagg_BillingResponse::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun cancelBilling(idBilling: String, callBack: PaggcertoCallBack<Boolean>){
        val call = appService?.create(BillingService::class.java)?.cancelBilling(idBilling)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(true)
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")
                    422 -> callBack.onError(response.code(), printError(response))
                    401 -> callBack.onError(response.code(), error401)
                    403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), "$unknownError - Erro ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }
}
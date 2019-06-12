package br.com.paggcerto.paggcertosdk.Rest.Payment

import br.com.paggcerto.paggcertosdk.Model.*
import br.com.paggcerto.paggcertosdk.PaggcertoCallBack
import br.com.paggcerto.paggcertosdk.PaggcertoSDK
import br.com.paggcerto.paggcertosdk.Rest.Payment.Service.BinService
import br.com.paggcerto.paggcertosdk.Rest.Payment.Service.PaymentService
import br.com.paggcerto.paggcertosdk.Util.JSONUtils
import br.com.paggcerto.paggcertosdk.Util.Util.printError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.json.JSONException

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import java.util.HashMap

class PaymentNetwork  {
    private val appService = PaymentClient.getClient(PaggcertoSDK.getInstance()?.token!!)

    private val connectionError = "Não foi possível conectar ao servidor Paggcerto. Tente novamente."

    private val error401 = "Usuário não autenticado (credenciais incorretas ou token inválido)"
    private val error403 = "Usuário autenticado, porém sem permissão (acesso negado)"
    private val unknownError = "Erro inesperado."

    private val gson = Gson()

    fun getBin(callBack: PaggcertoCallBack<ArrayList<Pagg_Bin>>){
        val call = appService?.create(BinService::class.java)?.getBins()
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> try{
                        val cardBrands = ArrayList<Pagg_Bin>()
                        val jsonObject = JSONObject(response.body())
                        val binsArray = jsonObject.getJSONArray("bins")

                        for (i in 0 until binsArray.length()) {
                            cardBrands.add(gson.fromJson<Pagg_Bin>(binsArray.get(i).toString(), Pagg_Bin::class.java))
                        }

                        callBack.onSuccess(cardBrands)
                    }catch (e: Exception){
                        e.printStackTrace()
                        callBack.onError(-1, "Erro ao processar bandeiras")
                    }
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun payWithCard(pay: Pagg_Pay, callBack: PaggcertoCallBack<Pagg_Payment>){

        val json = gson.toJson(pay)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call = appService?.create(PaymentService::class.java)?.payWithCard(dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Payment>(response.body(), Pagg_Payment::class.java))
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getTransferDays(callBack: PaggcertoCallBack<Pagg_TransferDays>){
        val call = appService?.create(PaymentService::class.java)?.getTransferDays()
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_TransferDays>(response.body(), Pagg_TransferDays::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun payments(filterHistoryPayment: Pagg_FilterHistoryPayment, callBack: PaggcertoCallBack<Pagg_HistoryPayments>){

        try {
            var json = gson.toJson(filterHistoryPayment)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()

            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call = appService?.create(PaymentService::class.java)?.getHistoryPayments(retMap)
            call?.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_HistoryPayments>(response.body(), Pagg_HistoryPayments::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        }catch (e: Exception){
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun findPayment(paymentId: String, callBack: PaggcertoCallBack<Pagg_Payment>){
        val call: Call<String>? = appService?.create(PaymentService::class.java)?.getPaymentDetail(paymentId)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Payment>(response.body(), Pagg_Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun sendReceipt(nsu: String, sendReceipt: Pagg_SendReceipt, callBack: PaggcertoCallBack<Boolean>){
        val json = gson.toJson(sendReceipt)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String>? = appService?.create(PaymentService::class.java)?.sendReceipt(nsu, dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 204 -> callBack.onSuccess(true)
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun pdfBankSlipPayment(paymentId: String, callBack: PaggcertoCallBack<ByteArray?>){
        val call: Call<ResponseBody>? = appService?.create(PaymentService::class.java)?.getBankSlipsPDF(paymentId)
        call?.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(response.body()?.bytes())
                    response.code() == 422 -> {
                        var errorKey = ""
                        try {
                            errorKey = JSONObject(response.errorBody()?.string()).getString("error")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            callBack.onError(response.code(), errorKey)
                        }
                    }
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun cancelCardTransaction(nsu: String, callBack: PaggcertoCallBack<Pagg_Payment>){
        val call: Call<String>? = appService?.create(PaymentService::class.java)?.cancelCardTransaction(nsu)
        call?.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Payment>(response.body(), Pagg_Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun cancelPayment(paymentId: String, callBack: PaggcertoCallBack<Pagg_Payment>){
        val call: Call<String>? = appService?.create(PaymentService::class.java)?.cancelPayment(paymentId)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Payment>(response.body(), Pagg_Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun bankSlipPay(bankSlipsPay: Pagg_BankSlipsPay, callBack: PaggcertoCallBack<Pagg_Payments>){
        val json = gson.toJson(bankSlipsPay)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String>? = appService?.create(PaymentService::class.java)?.payBankSlips(dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Payments>(response.body(), Pagg_Payments::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun finalizePayment(paymentId: String, note: String, callBack: PaggcertoCallBack<Pagg_Payment>){
        val json = "{\"note\":\"$note\"}"
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String>? = appService?.create(PaymentService::class.java)?.finishPayment(paymentId, dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Payment>(response.body(), Pagg_Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun cancelBankSlip(number: String, cancellationReason: String, callBack: PaggcertoCallBack<Pagg_Payment>){
        val json = "{\"cancellationNote\":\"$cancellationReason\"}"
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String>? = appService?.create(PaymentService::class.java)?.cancelBankSlip(number,  dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Payment>(response.body(), Pagg_Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun replaceBankslip(number: String, replaceBankSlips: Pagg_ReplaceBankSlips, callBack: PaggcertoCallBack<Pagg_Payment>){
        val json = gson.toJson(replaceBankSlips)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String>? = appService?.create(PaymentService::class.java)?.replaceBankslips(number, dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Payment>(response.body(), Pagg_Payment::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    internal fun historyAnticipation(filterAnticipation: Pagg_FilterAnticipation, callBack: PaggcertoCallBack<Pagg_AnticipationHistory>){

        try {
            var json = gson.toJson(filterAnticipation)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String>? = appService?.create(PaymentService::class.java)?.anticipationHistory(retMap)
            call?.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_AnticipationHistory>(response.body(), Pagg_AnticipationHistory::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    internal fun cardTransactionsAvailable(filterTransaction: Pagg_FilterTransaction, callBack: PaggcertoCallBack<Pagg_AnticipableTransactions>){
        try {
            var json = gson.toJson(filterTransaction)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String>? = appService?.create(PaymentService::class.java)?.anticipatedTransactions(retMap)
            call?.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_AnticipableTransactions>(response.body(), Pagg_AnticipableTransactions::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    internal fun requestAnticipation(transactionsToAnticipate: Pagg_TransactionsToAnticipate, callBack: PaggcertoCallBack<Pagg_Anticipation>){
        val json = gson.toJson(transactionsToAnticipate)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String>? = appService?.create(PaymentService::class.java)?.newAnticipationRequest(dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Anticipation>(response.body(), Pagg_Anticipation::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    internal fun anticipationTransactions(anticipationId: String, filterTransaction: Pagg_FilterTransaction, callBack: PaggcertoCallBack<Pagg_AnticipableTransactions>){
        try {
            var json = gson.toJson(filterTransaction)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String>? = appService?.create(PaymentService::class.java)?.anticipationTransactions(anticipationId, retMap)
            call?.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_AnticipableTransactions>(response.body(), Pagg_AnticipableTransactions::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun transfers(filterTransfer: Pagg_FilterTransfer, callBack: PaggcertoCallBack<Pagg_HistoryTransfer>){
        try {
            var json = gson.toJson(filterTransfer)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String>? = appService?.create(PaymentService::class.java)?.getHistoryTransfers(retMap)
            call?.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_HistoryTransfer>(response.body(), Pagg_HistoryTransfer::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun scheduledTransfers(filterTransfer: Pagg_FilterTransfer, callBack: PaggcertoCallBack<Pagg_HistoryTransfer>){
        try {
            var json = gson.toJson(filterTransfer)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String>? = appService?.create(PaymentService::class.java)?.getFutureTransfers(retMap)
            call?.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_HistoryTransfer>(response.body(), Pagg_HistoryTransfer::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun simulate(simulation: Pagg_Simulation, callBack: PaggcertoCallBack<Pagg_SimulationResult>){
        val json = gson.toJson(simulation)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String>? = appService?.create(PaymentService::class.java)?.simulatePay(dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_SimulationResult>(response.body(), Pagg_SimulationResult::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })

    }

    fun balance(callBack: PaggcertoCallBack<Pagg_Balance>){
        val call: Call<String>? = appService?.create(PaymentService::class.java)?.balance()
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Balance>(response.body(), Pagg_Balance::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun transfers(id: String, callBack: PaggcertoCallBack<Pagg_TransferDetail>){
        val call: Call<String>? = appService?.create(PaymentService::class.java)?.getTransfersDetail(id)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_TransferDetail>(response.body(), Pagg_TransferDetail::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun createCard(card: Pagg_Card, callBack: PaggcertoCallBack<Pagg_Card>){
        val json = gson.toJson(card)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String>? = appService?.create(PaymentService::class.java)?.createCard(dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Card>(response.body(), Pagg_Card::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun cards(filterCards: Pagg_FilterCards, callBack: PaggcertoCallBack<Pagg_CardList>){
        try {
            var json = gson.toJson(filterCards)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()
            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call: Call<String>? = appService?.create(PaymentService::class.java)?.cards(retMap)
            call?.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_CardList>(response.body(), Pagg_CardList::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                        response.code() == 422 -> callBack.onError(response.code(), printError(response))
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun findCard(idCard: String, callBack: PaggcertoCallBack<Pagg_Card>){
        val call: Call<String>? = appService?.create(PaymentService::class.java)?.findCard(idCard)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Card>(response.body(), Pagg_Card::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun deleteCard(idCard: String, callBack: PaggcertoCallBack<Pagg_Card>){
         val call: Call<String>? = appService?.create(PaymentService::class.java)?.removeCard(idCard)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Card>(response.body(), Pagg_Card::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun createSplitter(splitter: Pagg_Splitter, callBack: PaggcertoCallBack<Pagg_Splitter>){
        val json = gson.toJson(splitter)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String>? = appService?.create(PaymentService::class.java)?.registerSplitter(dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Splitter>(response.body(), Pagg_Splitter::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun updateSplitter(id: String, splitter: Pagg_Splitter, callBack: PaggcertoCallBack<Pagg_Splitter>){
        val json = gson.toJson(splitter)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        val call: Call<String>? = appService?.create(PaymentService::class.java)?.updateSplitter(id, dataObject)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Splitter>(response.body(), Pagg_Splitter::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun splitters(filterSplitter: Pagg_FilterSplitter, callBack: PaggcertoCallBack<Pagg_SplitterList>){
        try {
            var json = gson.toJson(filterSplitter)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()

            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call = appService?.create(PaymentService::class.java)?.listSplitter(retMap)
            call?.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_SplitterList>(response.body(), Pagg_SplitterList::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                        response.code() == 401 -> callBack.onError(response.code(), error401)
                        response.code() == 403 -> callBack.onError(response.code(), error403)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        }catch (e: Exception){
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }

    fun findSplitter(id: String, callBack: PaggcertoCallBack<Pagg_Splitter>){
        val call = appService?.create(PaymentService::class.java)?.getSplitter(id)
        call?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Splitter>(response.body(), Pagg_Splitter::class.java))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                    response.code() == 422 -> callBack.onError(response.code(), printError(response))
                    response.code() == 401 -> callBack.onError(response.code(), error401)
                    response.code() == 403 -> callBack.onError(response.code(), error403)
                    else -> callBack.onError(response.code(), unknownError)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callBack.onError(-1, connectionError)
            }
        })
    }

    fun getTransfersSplitter(filterTransferSplitter: Pagg_FilterTransferSplitter, callBack: PaggcertoCallBack<Pagg_TransfersSplit>){
        try {
            var json = gson.toJson(filterTransferSplitter)
            json = JSONUtils.removeArrays(JSONObject(json)).toString()

            val retMap = gson.fromJson<Map<String, String>>( json, object : TypeToken<HashMap<String, String>>() {}.type )

            val call = appService?.create(PaymentService::class.java)?.getTransferSplitters(retMap)
            call?.enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    when {
                        response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_TransfersSplit>(response.body(), Pagg_TransfersSplit::class.java))
                        response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()!!)
                        else -> callBack.onError(response.code(), unknownError)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    callBack.onError(-1, connectionError)
                }
            })

        }catch (e: Exception){
            e.printStackTrace()
            callBack.onError(-1, "Erro Inerperado")
        }
    }
}
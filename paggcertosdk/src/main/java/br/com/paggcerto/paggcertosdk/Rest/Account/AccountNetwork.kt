package br.com.paggcerto.paggcertosdk.Rest.Account

import br.com.paggcerto.paggcertosdk.Model.Account.Pagg_UserWhoAmI
import br.com.paggcerto.paggcertosdk.Model.Pagg_LoginForm
import br.com.paggcerto.paggcertosdk.Model.Pagg_Token
import br.com.paggcerto.paggcertosdk.PaggcertoCallBack
import br.com.paggcerto.paggcertosdk.Util.Util
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountNetwork(token: Pagg_Token? = null) {

    private val appService = AccountClient.getClient(token)

    private val connectionError = "Não foi possível conectar ao servidor Paggcerto. Tente novamente."

    private val error401 = "Usuário não autenticado (credenciais incorretas ou token inválido)"
    private val error403 = "Usuário autenticado, porém sem permissão (acesso negado)"
    private val unknownError = "Erro inesperado."

    private val gson = Gson()

    fun signin(loginForm: Pagg_LoginForm, callBack: PaggcertoCallBack<Pagg_Token>) {

        val json = gson.toJson(loginForm)
        val dataObject = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)

        appService?.create(AccountService::class.java)?.login(Util.PAGG_APP_ID, dataObject)?.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when {
                    response.code() == 200 -> callBack.onSuccess(gson.fromJson<Pagg_Token>(response.body(), Pagg_Token::class.java))
                    response.code() == 422 -> callBack.onError(response.code(), Util.printError(response))
                    response.code() == 400 -> callBack.onError(response.code(), response.errorBody()?.string()?: "Erro 400")
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

    fun identify(callBack: PaggcertoCallBack<Pagg_UserWhoAmI>){

        appService?.create(AccountService::class.java)?.identify()?.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                when(response.code()) {
                    200 -> callBack.onSuccess(gson.fromJson<Pagg_UserWhoAmI>(response.body(), Pagg_UserWhoAmI::class.java))
                    400 -> callBack.onError(response.code(), response.errorBody()?.string() ?: "Erro 400")

                    422 -> callBack.onError(response.code(), Util.printError(response))
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